package com.db.auction.exception;

public class AuctionInactiveException extends RuntimeException {

    public AuctionInactiveException(String message) {
        super(message);
    }
}
