package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/health")
@Tag(name = "Health Check", description = "Sprawdzanie stanu aplikacji")
public class HealthController {

    @Autowired(required = false)
    private DataSource dataSource;

    @GetMapping
    @Operation(summary = "Status aplikacji", description = "Sprawdza status aplikacji i połączenia z bazą danych")
    public ResponseEntity<Map<String, Object>> healthCheck() {
        Map<String, Object> health = new HashMap<>();
        health.put("status", "UP");
        health.put("timestamp", java.time.LocalDateTime.now());
        health.put("application", "Warehouse Manager");

        // Sprawdzenie połączenia z bazą danych - bezpieczne
        if (dataSource != null) {
            try (Connection connection = dataSource.getConnection()) {
                health.put("database", "Connected");
                health.put("databaseUrl", connection.getMetaData().getURL());
                health.put("databaseProduct", connection.getMetaData().getDatabaseProductName());
            } catch (Exception e) {
                health.put("database", "Disconnected");
                health.put("databaseError", e.getMessage());
            }
        } else {
            health.put("database", "DataSource not available");
        }

        return ResponseEntity.ok(health);
    }

    @GetMapping("/simple")
    @Operation(summary = "Prosty status", description = "Podstawowy status bez sprawdzania bazy danych")
    public ResponseEntity<Map<String, Object>> simpleHealthCheck() {
        Map<String, Object> health = new HashMap<>();
        health.put("status", "OK");
        health.put("timestamp", java.time.LocalDateTime.now());
        health.put("message", "Aplikacja działa poprawnie");
        return ResponseEntity.ok(health);
    }
}
