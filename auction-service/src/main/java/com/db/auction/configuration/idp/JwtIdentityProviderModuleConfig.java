package com.db.auction.configuration.idp;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

@Getter
@RequiredArgsConstructor
@Configuration(JwtIdentityProviderModuleConfig.BEAN_NAME)
public class JwtIdentityProviderModuleConfig extends BuiltInIdentityProviderConfig {
    public static final String BEAN_NAME = "dataSourceIdentityProviderModule";

}
