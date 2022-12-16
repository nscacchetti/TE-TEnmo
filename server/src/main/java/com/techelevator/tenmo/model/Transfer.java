package com.techelevator.tenmo.model;

import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Min;
import java.math.BigDecimal;

public class Transfer {
    private int transferId;
    private BigDecimal transferAmount;
    private int userTo;
    private int userFrom;
    @AssertTrue
    private boolean notSendingToSelf;
    @AssertTrue
    private boolean transactionGreaterThanZero;

//    @Range (min = 0, max = 10000)
//            @Range (min = -10000, max = 0)
//    int Match = userTo - userFrom;



//    @Min
//    boolean transferMoreThanZero = transferAmount.compareTo(BigDecimal.ZERO) > 0;

    public Transfer() {
       notSendingToSelf=true;

       transactionGreaterThanZero=true;
    }

    public Transfer(BigDecimal transferAmount, int userTo, int userFrom) {

        this.transferAmount = transferAmount;
        this.userTo = userTo;
        this.userFrom = userFrom;
        notSendingToSelf = userTo != userFrom;
        transactionGreaterThanZero = transferAmount.compareTo(BigDecimal.ZERO) > 0;

    }

    public Transfer(int transferId, BigDecimal transferAmount, int userTo, int userFrom) {
        this.transferId = transferId;
        this.transferAmount = transferAmount;
        this.userTo = userTo;
        this.userFrom = userFrom;
        notSendingToSelf = userTo != userFrom;
        transactionGreaterThanZero = transferAmount.compareTo(BigDecimal.ZERO) > 0;
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
