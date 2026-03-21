package gui;

import dao.ProjectDAO;
import model.Project;
import model.User;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class StudentDashboard extends JFrame {
    private ProjectDAO projectDAO;
    private JTable projectTable;
    private DefaultTableModel tableModel;

    public StudentDashboard(User student) {
        projectDAO = new ProjectDAO();
        setTitle("Student Dashboard - " + student.getUsername());
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        setLayout(new BorderLayout());
        
        // Table for Approved Projects
        String[] columns = {"ID", "Title", "Budget", "Deadline"};
        tableModel = new DefaultTableModel(columns, 0);
        projectTable = new JTable(tableModel);
        loadApprovedProjects();

        add(new JScrollPane(projectTable), BorderLayout.CENTER);

        // Buttons Panel
        JPanel btnPanel = new JPanel();
        JButton applyBtn = new JButton("Apply for Project");
        JButton viewSubmissionsBtn = new JButton("My Submissions");
        JButton logoutBtn = new JButton("Logout");

        btnPanel.add(applyBtn);
        btnPanel.add(viewSubmissionsBtn);
        btnPanel.add(logoutBtn);
        add(btnPanel, BorderLayout.SOUTH);

        applyBtn.addActionListener(e -> handleApply());
        logoutBtn.addActionListener(e -> {
            dispose();
            new LoginFrame().setVisible(true);
        });
    }

    private void loadApprovedProjects() {
        tableModel.setRowCount(0);
        List<Project> approved = projectDAO.getProjectsByStatus("APPROVED");
        for (Project p : approved) {
            tableModel.addRow(new Object[]{p.getProjectId(), p.getTitle(), p.getBudget() + "$", p.getDeadline()});
        }
    }

    private void handleApply() {
        int row = projectTable.getSelectedRow();
        if (row != -1) {
            int projectId = (int) tableModel.getValueAt(row, 0);
            String proposal = JOptionPane.showInputDialog(this, "Enter your proposal:");
            JOptionPane.showMessageDialog(this, "Application submitted for project ID: " + projectId);
            // Further logic for application storage can be added in ApplicationDAO
        } else {
            JOptionPane.showMessageDialog(this, "Select a project to apply");
        }
    }
}
