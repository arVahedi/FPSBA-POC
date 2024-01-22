package com.db.product.configuration;

import com.db.product.database.repository.BaseRepositoryImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(repositoryBaseClass = BaseRepositoryImpl.class, basePackages = "com.db.product")
@EnableTransactionManagement
public class DatasourceConfig implements BaseConfig {
}
