import java.sql.*;
import util.DBConnection;
public class TestSQLiteDate {
    public static void main(String[] args) {
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT deadline FROM projects LIMIT 1")) {
            if (rs.next()) {
                System.out.println("String format: " + rs.getString("deadline"));
                System.out.println("Date format: " + rs.getDate("deadline"));
            } else {
                System.out.println("No projects found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
