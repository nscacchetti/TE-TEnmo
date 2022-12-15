package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class Account {
    private BigDecimal balance = null;
    private int userID;
    private int accountID;

    public Account() {
    }

    public Account(BigDecimal balance, int userID, int accountID) {
        this.balance = balance;
        this.userID = userID;
        this.accountID = accountID;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getAccountID() {
        return accountID;
    }
    public BigDecimal increaseAccount (BigDecimal transferAmt){
        this.balance = this.balance.add(transferAmt);
        return balance;
    }

    public BigDecimal decreaseAccount (BigDecimal transferAmt) throws RuntimeException {
        if(this.balance.subtract(transfer).compareTo(BigDecimal.ZERO) >= 0){
            this.balance = this.balance.subtract(transferAmt);
            return balance;
        } else {
            throw new RuntimeException ("Not enough money in account.");
        }

    }


    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }
}
