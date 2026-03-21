import util.DBConnection;
import java.sql.*;

public class PopulateData {
    public static void main(String[] args) {
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            
            // Insert a Client
            stmt.execute("INSERT OR IGNORE INTO users (username, password, role, email) VALUES ('client1', 'pass123', 'CLIENT', 'client1@test.com')");
            
            // Get Client ID
            ResultSet rs = stmt.executeQuery("SELECT user_id FROM users WHERE username = 'client1'");
            int clientId = -1;
            if (rs.next()) clientId = rs.getInt("user_id");
            
            if (clientId != -1) {
                // Insert some Pending Projects
                stmt.execute("INSERT INTO projects (title, description, budget, deadline, client_id, status) VALUES " +
                             "('Website Redesign', 'Redesign the corporate website with modern UI.', 1500.0, '2026-06-01', " + clientId + ", 'PENDING')");
                stmt.execute("INSERT INTO projects (title, description, budget, deadline, client_id, status) VALUES " +
                             "('Mobile App Bug Fix', 'Fix notification bugs in the existing React Native app.', 500.0, '2026-05-15', " + clientId + ", 'PENDING')");
                stmt.execute("INSERT INTO projects (title, description, budget, deadline, client_id, status) VALUES " +
                             "('Data Analysis Report', 'Generate a quarterly data report using Python.', 800.0, '2026-04-30', " + clientId + ", 'PENDING')");
                System.out.println("Sample data populated successfully.");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
