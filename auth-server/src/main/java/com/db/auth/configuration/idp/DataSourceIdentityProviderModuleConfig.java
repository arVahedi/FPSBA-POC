package com.db.auth.configuration.idp;

import com.db.auth.database.repository.SellerInfoRepository;
import com.db.auth.database.repository.UserRepository;
import com.db.auth.service.DefaultUserDetailsService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;

@Getter
@RequiredArgsConstructor
@Configuration(DataSourceIdentityProviderModuleConfig.BEAN_NAME)
public class DataSourceIdentityProviderModuleConfig extends BuiltInIdentityProviderConfig {
    public static final String BEAN_NAME = "dataSourceIdentityProviderModule";

    private final UserRepository userRepository;
    private final SellerInfoRepository sellerInfoRepository;

    @Bean(DefaultUserDetailsService.BEAN_NAME)
    public UserDetailsService userDetailsService() {
        return new DefaultUserDetailsService(this.userRepository, this.sellerInfoRepository);
    }
}
