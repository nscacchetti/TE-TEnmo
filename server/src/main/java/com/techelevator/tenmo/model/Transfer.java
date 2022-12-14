package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class Transfer {
    private BigDecimal transferAmount;
    private int userTo;
    private int userFrom;

    public Transfer(BigDecimal transferAmount, int userTo, int userFrom) {
        this.transferAmount = transferAmount;
        this.userTo = userTo;
        this.userFrom = userFrom;
    }

    public BigDecimal getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(BigDecimal transferAmount) {
        this.transferAmount = transferAmount;
    }

    public int getUserTo() {
        return userTo;
    }

    public void setUserTo(int userTo) {
        this.userTo = userTo;
    }

    public int getUserFrom() {
        return userFrom;
    }

    public void setUserFrom(int userFrom) {
        this.userFrom = userFrom;
    }
}
