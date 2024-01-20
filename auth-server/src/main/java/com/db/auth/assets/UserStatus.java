package com.db.auth.assets;

import com.db.auth.utility.jpa.PersistableEnumConverter;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(enumAsRef = true)
public enum UserStatus implements PersistableEnum<UserStatus> {
    ACTIVE(1),
    INACTIVE(2),
    SUSPEND(3);

    private final Integer code;

    UserStatus(Integer code) {
        this.code = code;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }

    public static class Converter extends PersistableEnumConverter<UserStatus> {
    }
}
