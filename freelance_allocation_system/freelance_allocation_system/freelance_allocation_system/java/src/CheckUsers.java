import util.DBConnection;
import java.sql.*;

public class CheckUsers {
    public static void main(String[] args) {
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM users")) {
            System.out.println("Users in database:");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("user_id") + 
                                   ", Username: " + rs.getString("username") + 
                                   ", Password: " + rs.getString("password") + 
                                   ", Role: " + rs.getString("role"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
