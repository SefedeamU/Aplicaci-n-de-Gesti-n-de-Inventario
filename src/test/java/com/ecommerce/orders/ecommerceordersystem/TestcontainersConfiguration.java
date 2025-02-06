package com.ecommerce.orders.ecommerceordersystem;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.utility.DockerImageName;

@Configuration
public class TestcontainersConfiguration {

    @Bean
    public MongoDBContainer mongoDbContainer() {
        if (isDockerAvailable()) {
            MongoDBContainer mongoDbContainer = new MongoDBContainer(DockerImageName.parse("mongo:4.4.2"));
            mongoDbContainer.start();
            return mongoDbContainer;
        } else {
            throw new IllegalStateException("Docker is not available");
        }
    }

    private boolean isDockerAvailable() {
        try {
            Process process = Runtime.getRuntime().exec("docker info");
            int exitCode = process.waitFor();
            return exitCode == 0;
        } catch (Exception e) {
            return false;
        }
    }
}