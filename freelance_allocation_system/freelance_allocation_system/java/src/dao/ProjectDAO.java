package dao;

import model.Project;
import util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProjectDAO {
    public boolean postProject(String title, String description, double budget, String deadline, int clientId) {
        String sql = "INSERT INTO projects (title, description, budget, deadline, client_id, status) VALUES (?, ?, ?, ?, ?, 'PENDING')";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, title);
            stmt.setString(2, description);
            stmt.setDouble(3, budget);
            stmt.setString(4, deadline);
            stmt.setInt(5, clientId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Project> getProjectsByStatus(String status) {
        List<Project> projects = new ArrayList<>();
        String sql = "SELECT * FROM projects WHERE status = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String dateStr = rs.getString("deadline");
                java.sql.Date deadline = null;
                if (dateStr != null && !dateStr.trim().isEmpty()) {
                    try {
                        deadline = java.sql.Date.valueOf(dateStr.split(" ")[0]);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                projects.add(new Project(
                    rs.getInt("project_id"),
                    rs.getString("title"),
                    rs.getString("description"),
                    rs.getDouble("budget"),
                    deadline,
                    rs.getInt("client_id"),
                    rs.getString("status")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return projects;
    }

    public boolean updateProjectStatus(int projectId, String status, String reason) {
        String sql = "UPDATE projects SET status = ?, rejection_reason = ? WHERE project_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status);
            stmt.setString(2, reason);
            stmt.setInt(3, projectId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
