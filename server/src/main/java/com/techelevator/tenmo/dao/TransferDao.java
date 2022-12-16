package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.util.List;

public interface TransferDao {

    List<Transfer> getAll();

    int addTransfer(Transfer transfer);

    Transfer getByTransfer(int transferId);
    List<Transfer> getByUserId(int userId);
}
