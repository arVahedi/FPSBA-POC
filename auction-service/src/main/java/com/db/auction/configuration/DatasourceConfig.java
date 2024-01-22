package com.db.auction.configuration;

import com.db.auction.database.repository.BaseRepositoryImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(repositoryBaseClass = BaseRepositoryImpl.class, basePackages = "com.db.auction")
@EnableTransactionManagement
public class DatasourceConfig implements BaseConfig {
}
