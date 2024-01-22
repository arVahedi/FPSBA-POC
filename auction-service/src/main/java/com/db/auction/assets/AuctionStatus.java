package com.db.auction.assets;

import com.db.lib.assets.PersistableEnum;
import com.db.lib.utility.jpa.PersistableEnumConverter;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(enumAsRef = true)
public enum AuctionStatus implements PersistableEnum<AuctionStatus> {

    ACTIVE(1),
    SUSPENDED(2),
    EXPIRED(3);

    private final Integer code;

    AuctionStatus(Integer code) {
        this.code = code;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }

    public static class Converter extends PersistableEnumConverter<AuctionStatus> {
    }
}
