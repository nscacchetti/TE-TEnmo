package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.util.List;

public interface TransferDao {

    List<Transfer> getAll();

    void addTransfer(Transfer transfer);

    Transfer get(int transferId);

}
