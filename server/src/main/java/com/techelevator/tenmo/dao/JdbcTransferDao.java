package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.util.ArrayList;
import java.util.List;

public class JdbcTransferDao implements TransferDao{

    private JdbcTemplate jdbcTemplate;

    public JdbcTransferDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Transfer> getAll() {
        List<Transfer> accounts = new ArrayList<>();
        String sql = "Select * FROM transfers";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while (results.next()){
            Transfer transfer = mapRowToTransfer(results);
            accounts.add(transfer);
        }
        return accounts;
    }

    @Override
    public int addTransfer(Transfer transfer) {
        String sql = "INSERT INTO transfers (from_user_id, to_user_id, transfer_amt) VALUES (?, ?, ?) RETURNING transfer_id";
        Integer newTransferId;
        try {
            newTransferId = jdbcTemplate.queryForObject(sql, Integer.class, transfer.getUserFrom(), transfer.getUserTo(), transfer.getTransferAmount());
        } catch (DataAccessException e) {
            return -1;
        }
        return newTransferId;
    }

    @Override
    public Transfer get(int transferId) {
        String sql = "Select * FROM transfers WHERE transfer_id = ?";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, transferId);
        if (rowSet.next()){
            Transfer transfer = mapRowToTransfer(rowSet);

            return transfer;
        }
        return null;
    }

    private Transfer mapRowToTransfer(SqlRowSet rowSet) {
        Transfer transfer = new Transfer();
        transfer.setTransferId(rowSet.getInt("transfer_id"));
        transfer.setUserFrom(rowSet.getInt("from_user_id"));
        transfer.setUserTo(rowSet.getInt("to_user_id"));
        transfer.setTransferAmount(rowSet.getBigDecimal("transfer_amt"));
        return transfer;

    }
}
