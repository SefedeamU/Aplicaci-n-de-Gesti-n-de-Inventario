package com.ecommerce.orders.ecommerceordersystem.config;

import io.r2dbc.spi.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.r2dbc.connection.R2dbcTransactionManager;
import org.springframework.transaction.ReactiveTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactoryOptions;

import static io.r2dbc.pool.PoolingConnectionFactoryProvider.MAX_SIZE;
import static io.r2dbc.spi.ConnectionFactoryOptions.*;

@TestConfiguration
@EnableTransactionManagement
@EnableR2dbcAuditing
@EnableR2dbcRepositories(
        basePackages = {
                "com.ecommerce.orders.ecommerceordersystem.user.repository",
                "com.ecommerce.orders.ecommerceordersystem.repository"
        },
        entityOperationsRef = "r2dbcEntityTemplate"
)
@Profile("test")
public class TestConfig {

    @Value("${spring.r2dbc.url}")
    private String r2dbcUrl;

    @Value("${spring.r2dbc.username}")
    private String r2dbcUsername;

    @Value("${spring.r2dbc.password}")
    private String r2dbcPassword;

    @Bean
    public ConnectionFactory connectionFactory() {
        return ConnectionFactories.get(ConnectionFactoryOptions.parse(r2dbcUrl)
                .mutate()
                .option(USER, r2dbcUsername)
                .option(PASSWORD, r2dbcPassword)
                .option(MAX_SIZE, 10)
                .build());
    }

    @Bean
    public R2dbcEntityTemplate r2dbcEntityTemplate(ConnectionFactory connectionFactory) {
        return new R2dbcEntityTemplate(connectionFactory);
    }

    @Bean
    public ReactiveTransactionManager transactionManager(ConnectionFactory connectionFactory) {
        return new R2dbcTransactionManager(connectionFactory);
    }
}