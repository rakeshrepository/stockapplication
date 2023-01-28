package com.stock.app.stockapplication.service;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;

public class IntegrationSetUpTest {
    @Container
    public static PostgreSQLContainer postgresContainer = new PostgreSQLContainer("postgres:latest")
            .withDatabaseName("test_stock_application");

    @BeforeAll
    public static void setUp() {
        StockServiceImpTest.postgresContainer.withReuse(true);
        StockServiceImpTest.postgresContainer.start();
    }

    @AfterAll
    public static void tearDown() {
        StockServiceImpTest.postgresContainer.stop();
    }

    @DynamicPropertySource
    public static void overrideProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", StockServiceImpTest.postgresContainer::getJdbcUrl);
        registry.add("spring.datasource.username", StockServiceImpTest.postgresContainer::getUsername);
        registry.add("spring.datasource.password", StockServiceImpTest.postgresContainer::getPassword);
    }
}
