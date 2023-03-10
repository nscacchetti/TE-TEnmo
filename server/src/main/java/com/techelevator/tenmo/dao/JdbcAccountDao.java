package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class JdbcAccountDao implements AccountDao{

    private JdbcTemplate jdbcTemplate;

    public JdbcAccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Account> getAll() {
        List<Account> accounts = new ArrayList<>();
        String sql = "Select * FROM account";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while (results.next()){
            Account account = mapRowToAccount(results);
            accounts.add(account);
        }
        return accounts;
    }

    @Override
    public Account get(int userId) {
        String sql = "Select * FROM account WHERE user_id = ?";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, userId);
        if (rowSet.next()){
            Account account = mapRowToAccount(rowSet);

                return account;
            }

        return null;
    }

    @Override
    public int create(Account account) {
        String sql = "INSERT INTO account (user_id, balance) VALUES (?, ?) RETURNING account_id";
        Integer newAccountId;
        try {
            newAccountId = jdbcTemplate.queryForObject(sql, Integer.class, account.getUserID(), account.getBalance());
        } catch (DataAccessException e) {
            return -1;
        }
        return newAccountId;
    }

    @Override
    public boolean update(Account account) {
        String sql = "UPDATE account SET balance = ? WHERE user_id = ?;";
//        boolean updatesSuccessful;

        try{
            jdbcTemplate.queryForObject(sql, Account.class, account.getBalance(), account.getUserID());
        } catch (DataAccessException e){
            return false;
        }

        return true;
    }


    private Account mapRowToAccount(SqlRowSet rowSet) {
        Account account = new Account();
        account.setAccountID(rowSet.getInt("account_id"));
        account.setUserID(rowSet.getInt("user_id"));
        account.setBalance(rowSet.getBigDecimal("balance"));
        return account;

    }

}
