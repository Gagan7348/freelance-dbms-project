package gui;

import dao.ProjectDAO;
import model.Project;
import model.User;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AdminDashboard extends JFrame {
    private ProjectDAO projectDAO;
    private JTable projectTable;
    private DefaultTableModel tableModel;

    public AdminDashboard(User admin) {
        projectDAO = new ProjectDAO();
        setTitle("Admin Dashboard - " + admin.getUsername());
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        setLayout(new BorderLayout());
        
        // Table for Pending Projects
        String[] columns = {"ID", "Title", "Budget", "Deadline", "Status"};
        tableModel = new DefaultTableModel(columns, 0);
        projectTable = new JTable(tableModel);
        loadPendingProjects();

        add(new JScrollPane(projectTable), BorderLayout.CENTER);

        // Buttons Panel
        JPanel btnPanel = new JPanel();
        JButton approveBtn = new JButton("Approve Project");
        JButton rejectBtn = new JButton("Reject Project");
        JButton logoutBtn = new JButton("Logout");

        btnPanel.add(approveBtn);
        btnPanel.add(rejectBtn);
        btnPanel.add(logoutBtn);
        add(btnPanel, BorderLayout.SOUTH);

        approveBtn.addActionListener(e -> handleApprove());
        rejectBtn.addActionListener(e -> handleReject());
        logoutBtn.addActionListener(e -> {
            dispose();
            new LoginFrame().setVisible(true);
        });
    }

    private void loadPendingProjects() {
        tableModel.setRowCount(0);
        List<Project> pending = projectDAO.getProjectsByStatus("PENDING");
        for (Project p : pending) {
            tableModel.addRow(new Object[]{p.getProjectId(), p.getTitle(), p.getBudget() + "$", p.getDeadline(), p.getStatus()});
        }
    }

    private void handleApprove() {
        int row = projectTable.getSelectedRow();
        if (row != -1) {
            int projectId = (int) tableModel.getValueAt(row, 0);
            if (projectDAO.updateProjectStatus(projectId, "APPROVED", null)) {
                JOptionPane.showMessageDialog(this, "Project Approved!");
                loadPendingProjects();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Select a project to approve");
        }
    }

    private void handleReject() {
        int row = projectTable.getSelectedRow();
        if (row != -1) {
            int projectId = (int) tableModel.getValueAt(row, 0);
            String reason = JOptionPane.showInputDialog(this, "Enter Rejection Reason:");
            if (projectDAO.updateProjectStatus(projectId, "REJECTED", reason)) {
                JOptionPane.showMessageDialog(this, "Project Rejected!");
                loadPendingProjects();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Select a project to reject");
        }
    }
}
