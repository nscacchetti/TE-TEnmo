package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.util.ArrayList;
import java.util.List;

public class MemoryTransferDao implements TransferDao{

    List<Transfer> transfers = new ArrayList<>();

    @Override
    public List<Transfer> getAll() {
        return transfers;
    }

    @Override
    public void addTransfer(Transfer transfer) {

        transfers.add(transfer);
    }

    @Override
    public Transfer get(int transferId) {

        for (Transfer transfer : transfers){
            if(transfer.getTransferId() == transferId){
                return transfer;
            }
        }

        return null;
    }
}
