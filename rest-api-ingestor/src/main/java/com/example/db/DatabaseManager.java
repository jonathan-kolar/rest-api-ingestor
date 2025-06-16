package com.example.db;

import com.example.model.APODResponse;
import java.sql.*;

public class DatabaseManager {
    private static final String DB_URL = "jdbc:h2:./NasaAPOD";

    public DatabaseManager() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
            Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(
                """
                        CREATE TABLE IF NOT EXISTS APODHistory (
                            id IDENTITY PRIMARY KEY,
                            copyright VARCHAR(255), 
                            date VARCHAR(12),
                            explanation CLOB,
                            hdurl VARCHAR(512),
                            media_type VARCHAR(255),
                            service_version VARCHAR(512),
                            title VARCHAR(255),
                            url VARCHAR(512)
                        )
                        """
            );
        } catch (SQLException e) {
            throw new RuntimeException("Failed to initialize DB", e);
        }
    }

    public void save(APODResponse apod) {
        try (Connection conn = DriverManager.getConnection(DB_URL);
            PreparedStatement ps = conn.prepareStatement("""
                INSERT INTO APODHistory (copyright, date, explanation, hdurl, media_type, service_version, title, url)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?)
            """)) {
            ps.setString(1, apod.copyright);
            ps.setString(2, apod.date);
            ps.setString(3, apod.explanation);
            ps.setString(4, apod.hdurl);
            ps.setString(5, apod.media_type);
            ps.setString(6, apod.service_version);
            ps.setString(7, apod.title);
            ps.setString(8, apod.url);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to save APOD entry", e);
        }
    }
}