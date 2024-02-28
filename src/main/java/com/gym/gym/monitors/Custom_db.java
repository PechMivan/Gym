package com.gym.gym.monitors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
public class Custom_db implements HealthIndicator {

    @Value("${database.name}")
    private String DB_NAME;

    @Value("${spring.datasource.url}")
    private String DB_URL;

    @Value("${spring.datasource.username}")
    private String DB_USER;

    @Value("${spring.datasource.password}")
    private String DB_PASS;

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
            String url = DB_URL;
            String username = DB_USER;
            String password = DB_PASS;

            // Verify if db is connected
            connection = DriverManager.getConnection(url, username, password);

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
