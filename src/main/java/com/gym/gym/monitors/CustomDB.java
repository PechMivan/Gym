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

    private final String dbName;
    private final String dbUrl;
    private final String dbUser;
    private final String dbPass;

    CustomDB(@Value("${database.name}") String dbName,
              @Value("${spring.datasource.url}") String dbUrl,
              @Value("${spring.datasource.username}") String dbUser,
              @Value("${spring.datasource.password}") String dbPass){

        this.dbName = dbName;
        this.dbUrl = dbUrl;
        this.dbUser = dbUser;
        this.dbPass = dbPass;
    }

    @Override
    public Health health() {
        if(isDatabaseConnected()){
            return Health.up().withDetail(dbName, "Service is running...").build();
        }
        return Health.down().withDetail(dbName, "Service is not available").build();
    }

    private boolean isDatabaseConnected(){
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPass);
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
