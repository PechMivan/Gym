package com.gym.gym.monitors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
@SuppressWarnings("unused")
public class CustomDB implements HealthIndicator {

    private final String DB_NAME;
    private final String DB_URL;
    private final String DB_USER;
    private final String DB_PASS;

    CustomDB(@Value("${database.name}") String DB_NAME,
              @Value("${spring.datasource.url}") String DB_URL,
              @Value("${spring.datasource.username}") String DB_USER,
              @Value("${spring.datasource.password}") String DB_PASS){

        this.DB_NAME = DB_NAME;
        this.DB_URL = DB_URL;
        this.DB_USER= DB_USER;
        this.DB_PASS = DB_PASS;
    }

    @Override
    public Health health() {
        if(isDatabaseConnected()){
            return Health.up().withDetail(DB_NAME, "Service is running...").build();
        }
        return Health.down().withDetail(DB_NAME, "Service is not available").build();
    }

    private boolean isDatabaseConnected(){
        Connection connection = null;
        try {
            // Verify if db is connected
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);

            // Verify with a ping if server is available
            String pingQuery = "/* ping */ SELECT 1";
            connection.createStatement().executeQuery(pingQuery);
            return true;
        } catch (SQLException e) {
            // Not connected or available
            return false;
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.err.println("Error closing JDBC connection: " + e.getMessage());
                    //TODO: Throw and manage this kind of exception.
                }
            }
        }
    }
}
