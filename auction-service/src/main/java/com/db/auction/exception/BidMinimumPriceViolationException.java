package com.db.auction.exception;

public class BidMinimumPriceViolationException extends RuntimeException {

    public BidMinimumPriceViolationException(String message) {
        super(message);
    }
}
