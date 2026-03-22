package gui;

import dao.ProjectDAO;
import model.Project;
import model.User;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class ClientDashboard extends JFrame {
    private ProjectDAO projectDAO;
    private User client;

    public ClientDashboard(User client) {
        this.client = client;
        this.projectDAO = new ProjectDAO();
        setTitle("Client Dashboard - " + client.getUsername());
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create tabbed pane
        JTabbedPane tabbedPane = new JTabbedPane();

        // Tab 1: Post New Project
        tabbedPane.addTab("Post Project", createPostProjectPanel());

        // Tab 2: Manage Deadlines
        tabbedPane.addTab("Manage Deadlines", createManageDeadlinesPanel());

        add(tabbedPane);
        setVisible(true);
    }

    private JPanel createPostProjectPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(new JLabel("Welcome " + client.getUsername() + "! Post a new project below:"), gbc);

        gbc.gridy = 1; gbc.gridwidth = 1;
        panel.add(new JLabel("Title:"), gbc);
        gbc.gridx = 1;
        JTextField titleField = new JTextField(25);
        panel.add(titleField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Description:"), gbc);
        gbc.gridx = 1;
        JTextArea descArea = new JTextArea(3, 25);
        panel.add(new JScrollPane(descArea), gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(new JLabel("Budget:"), gbc);
        gbc.gridx = 1;
        JTextField budgetField = new JTextField(25);
        panel.add(budgetField, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        panel.add(new JLabel("Deadline:"), gbc);
        gbc.gridx = 1;
        JTextField deadlineField = new JTextField(25);
        deadlineField.setText(LocalDate.now().plusDays(30).toString());
        deadlineField.setToolTipText("Format: YYYY-MM-DD");
        panel.add(deadlineField, gbc);

        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        JButton postBtn = new JButton("Post Project");
        panel.add(postBtn, gbc);

        gbc.gridy = 6;
        JButton logoutBtn = new JButton("Logout");
        panel.add(logoutBtn, gbc);

        postBtn.addActionListener(e -> {
            try {
                String title = titleField.getText();
                String desc = descArea.getText();
                double budget = Double.parseDouble(budgetField.getText());
                String deadline = deadlineField.getText();

                if (title.isEmpty() || desc.isEmpty() || deadline.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please fill in all fields!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (projectDAO.postProject(title, desc, budget, deadline, client.getUserId())) {
                    JOptionPane.showMessageDialog(this, "Project posted for Admin approval!");
                    titleField.setText("");
                    descArea.setText("");
                    budgetField.setText("");
                    deadlineField.setText(LocalDate.now().plusDays(30).toString());
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to post project!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid budget!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        logoutBtn.addActionListener(e -> {
            dispose();
            new LoginFrame().setVisible(true);
        });

        return panel;
    }

    private JPanel createManageDeadlinesPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("Manage Project Deadlines");
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
            loadClientProjects(projectListPanel);
            projectListPanel.revalidate();
            projectListPanel.repaint();
        });

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(refreshBtn);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        // Load initial projects
        loadClientProjects(projectListPanel);

        return panel;
    }

    private void loadClientProjects(JPanel projectListPanel) {
        projectListPanel.removeAll();
        List<Project> projects = projectDAO.getProjectsByClientId(client.getUserId());

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
                loadClientProjects(parentPanel);
                parentPanel.revalidate();
                parentPanel.repaint();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update deadline!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        panel.add(updateBtn, gbc);

        return panel;
    }
}
