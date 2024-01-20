package com.db.auth.assets;

import org.jose4j.jwt.ReservedClaimNames;

public class ClaimName extends ReservedClaimNames {

    public static final String UID = "uid";
    public static final String ROLES = "roles";

    private ClaimName() {
        throw new AssertionError("Instance creation of this class is illegal");
    }
}
