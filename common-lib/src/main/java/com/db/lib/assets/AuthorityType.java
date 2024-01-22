package com.db.lib.assets;


import lombok.Getter;

@Getter
public enum AuthorityType {
    MONITORING_AUTHORITY(StringFormat.MONITORING_AUTHORITY),
    SELLER_AUTHORITY(StringFormat.SELLER_AUTHORITY);

    private final String value;

    AuthorityType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }

    public static class StringFormat {
        public static final String MONITORING_AUTHORITY = "MONITORING_AUTHORITY";
        public static final String SELLER_AUTHORITY = "SELLER_AUTHORITY";

        private StringFormat() {
            throw new AssertionError("This is a utility class and cannot be instantiated");
        }
    }
}
