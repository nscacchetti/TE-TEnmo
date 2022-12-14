package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;

import java.util.List;

public interface AccountDao {

    List<Account> getAll();

    Account get(int accountId);

    void create(Account account);

}
