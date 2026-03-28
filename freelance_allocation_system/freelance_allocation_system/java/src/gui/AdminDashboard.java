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
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create tabbed pane
        JTabbedPane tabbedPane = new JTabbedPane();

        // Tab 1: Pending Projects
        tabbedPane.addTab("Pending Projects", createPendingProjectsPanel());

        // Tab 2: Manage Deadlines
        tabbedPane.addTab("Manage Deadlines", createManageDeadlinesPanel());

        add(tabbedPane);
    }

    private JPanel createPendingProjectsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // Table for Pending Projects
        String[] columns = {"ID", "Title", "Budget", "Deadline", "Status"};
        tableModel = new DefaultTableModel(columns, 0);
        projectTable = new JTable(tableModel);
        loadPendingProjects();

        panel.add(new JScrollPane(projectTable), BorderLayout.CENTER);

        // Buttons Panel
        JPanel btnPanel = new JPanel();
        JButton approveBtn = new JButton("Approve Project");
        JButton rejectBtn = new JButton("Reject Project");

        btnPanel.add(approveBtn);
        btnPanel.add(rejectBtn);
        panel.add(btnPanel, BorderLayout.SOUTH);

        approveBtn.addActionListener(e -> handleApprove());
        rejectBtn.addActionListener(e -> handleReject());

        return panel;
    }

    private JPanel createManageDeadlinesPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("Manage All Project Deadlines");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(titleLabel, BorderLayout.NORTH);

        // Create a panel for project list
        JPanel projectListPanel = new JPanel();
        projectListPanel.setLayout(new BoxLayout(projectListPanel, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(projectListPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Refresh button
        JButton refreshBtn = new JButton("Refresh");
        refreshBtn.addActionListener(e -> {
            projectListPanel.removeAll();
            loadAllProjects(projectListPanel);
            projectListPanel.revalidate();
            projectListPanel.repaint();
        });

        // Logout button
        JButton logoutBtn = new JButton("Logout");
        logoutBtn.addActionListener(e -> {
            dispose();
            new LoginFrame().setVisible(true);
        });

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(refreshBtn);
        bottomPanel.add(logoutBtn);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        // Load initial projects
        loadAllProjects(projectListPanel);

        return panel;
    }

    private void loadAllProjects(JPanel projectListPanel) {
        projectListPanel.removeAll();
        List<Project> projects = projectDAO.getAllProjects();

        if (projects.isEmpty()) {
            projectListPanel.add(new JLabel("No projects found."));
            return;
        }

        for (Project project : projects) {
            JPanel projectPanel = createProjectDeadlinePanel(project, projectListPanel);
            projectListPanel.add(projectPanel);
            projectListPanel.add(Box.createVerticalStrut(10));
        }
    }

    private JPanel createProjectDeadlinePanel(Project project, JPanel parentPanel) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder(project.getTitle()));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Project ID: " + project.getProjectId()), gbc);

        gbc.gridy = 1;
        panel.add(new JLabel("Status: " + project.getStatus()), gbc);

        gbc.gridy = 2;
        panel.add(new JLabel("Current Deadline:"), gbc);
        gbc.gridx = 1;
        String currentDeadline = project.getDeadline() != null ? project.getDeadline().toString() : "Not set";
        panel.add(new JLabel(currentDeadline), gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(new JLabel("New Deadline:"), gbc);
        gbc.gridx = 1;
        JTextField newDeadlineField = new JTextField(15);
        newDeadlineField.setToolTipText("Format: YYYY-MM-DD");
        panel.add(newDeadlineField, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        JButton updateBtn = new JButton("Update Deadline");
        updateBtn.addActionListener(e -> {
            String newDeadline = newDeadlineField.getText();
            if (newDeadline.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a deadline!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (projectDAO.updateDeadline(project.getProjectId(), newDeadline)) {
                JOptionPane.showMessageDialog(this, "Deadline updated successfully!");
                newDeadlineField.setText("");
                // Refresh the deadline display
                parentPanel.removeAll();
                loadAllProjects(parentPanel);
                parentPanel.revalidate();
                parentPanel.repaint();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update deadline!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        panel.add(updateBtn, gbc);

        return panel;
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
