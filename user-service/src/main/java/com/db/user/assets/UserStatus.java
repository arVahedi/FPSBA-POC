package com.db.user.assets;

import com.db.lib.assets.PersistableEnum;
import com.db.lib.utility.jpa.PersistableEnumConverter;
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
