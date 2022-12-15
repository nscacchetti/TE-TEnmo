package com.techelevator.tenmo.model;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Min;
import java.math.BigDecimal;

public class Transfer {
    private int transferId;
    private BigDecimal transferAmount;
    private int userTo;
    private int userFrom;

    @AssertTrue
    boolean usersMatch = userTo != userFrom;

    @AssertTrue
    boolean transferMoreThanZero = transferAmount.compareTo(BigDecimal.ZERO) > 0;

    public Transfer() {
    }

    public Transfer(int transferId, BigDecimal transferAmount, int userTo, int userFrom) {
        this.transferId = transferId;
        this.transferAmount = transferAmount;
        this.userTo = userTo;
        this.userFrom = userFrom;
    }

    public int getTransferId() {
        return transferId;
    }

    public void setTransferId(int transferId) {
        this.transferId = transferId;
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
