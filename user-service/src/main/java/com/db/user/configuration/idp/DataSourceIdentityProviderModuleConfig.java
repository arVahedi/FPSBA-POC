package com.db.user.configuration.idp;

import com.db.user.database.repository.SellerInfoRepository;
import com.db.user.database.repository.UserRepository;
import com.db.user.service.DefaultUserDetailsService;
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
