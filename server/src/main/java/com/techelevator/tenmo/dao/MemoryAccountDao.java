package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;

import java.util.ArrayList;
import java.util.List;

public class MemoryAccountDao implements AccountDao{

    List<Account> accounts = new ArrayList<>();

    @Override
    public List<Account> getAll() {
        return accounts;
    }

    @Override
    public Account get(int accountId) {

        for (Account account : accounts){
            if (account.getAccountID() == accountId){
                return account;
            }
        }

        return null;
    }

    @Override
    public void create(Account account) {

        accounts.add(account);

//        return accounts;
    }
}
