package com.db.auction.assets;


import lombok.Getter;

/**
 * An enum container for all business errors
 */

@Getter
public enum ErrorCode {
    EXPIRED_DATA(6001, "EXPIRED_DATA", "user_message_expired_data")      // Expired data
    ;

    private final int code;
    private final String description;
    private final String i18nKey;

    ErrorCode(int code, String description, String i18nKey) {
        this.code = code;
        this.description = description;
        this.i18nKey = i18nKey;
    }
}