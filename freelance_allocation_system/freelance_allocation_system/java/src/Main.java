package main;

import gui.LoginFrame;
import util.DBConnection;
import javax.swing.SwingUtilities;
import java.sql.Connection;
import java.sql.Statement;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        setupDatabase();
        SwingUtilities.invokeLater(() -> {
            new LoginFrame().setVisible(true);
        });
    }

    private static void setupDatabase() {
        try (Connection conn = DBConnection.getConnection();
                Statement stmt = conn.createStatement()) {
            String schema = new String(Files.readAllBytes(Paths.get("database/schema.sqlite.sql")));
            for (String sql : schema.split(";")) {
                if (!sql.trim().isEmpty()) {
                    stmt.execute(sql);
                }
            }
            System.out.println("Database setup complete.");
        } catch (Exception e) {
            System.err.println("Database setup failed: " + e.getMessage());
        }
    }
}
