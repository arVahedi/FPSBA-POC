package com.db.auth.configuration;

import com.db.auth.database.repository.BaseRepositoryImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(repositoryBaseClass = BaseRepositoryImpl.class, basePackages = "com.db.auth")
@EnableTransactionManagement
public class DatasourceConfig implements BaseConfig {
}
