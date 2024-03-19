package com.gym.gym.monitors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

@Component
@SuppressWarnings("unused")
public class CustomDB implements HealthIndicator {

    private final String DB_NAME;
    private final String DB_URL;
    private final String DB_USER;
    private final String DB_PASS;

    CustomDB(@Value("${database.name}") String dbName,
              @Value("${spring.datasource.url}") String dbUrl,
              @Value("${spring.datasource.username}") String dbUser,
              @Value("${spring.datasource.password}") String dbPass){

        this.DB_NAME = dbName;
        this.DB_URL = dbUrl;
        this.DB_USER= dbUser;
        this.DB_PASS = dbPass;
    }

    @Override
    public Health health() {
        if(isDatabaseConnected()){
            return Health.up().withDetail(DB_NAME, "Service is running...").build();
        }
        return Health.down().withDetail(DB_NAME, "Service is not available").build();
    }

    private boolean isDatabaseConnected(){
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             Statement statement = connection.createStatement()){
            // Verify with a ping if server is available
            String pingQuery = "/* ping */ SELECT 1";
            statement.executeQuery(pingQuery);
            return true;
        } catch (SQLException e) {
            // Not connected or available
            return false;
        }
    }
}
