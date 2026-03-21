package gui;

import dao.ProjectDAO;
import model.User;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class ClientDashboard extends JFrame {
    private ProjectDAO projectDAO;
    private User client;

    public ClientDashboard(User client) {
        this.client = client;
        this.projectDAO = new ProjectDAO();
        setTitle("Client Dashboard - " + client.getUsername());
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        add(new JLabel("Welcome " + client.getUsername() + "! Post a new project below:"), gbc);

        gbc.gridy = 1; gbc.gridwidth = 1;
        add(new JLabel("Title:"), gbc);
        gbc.gridx = 1;
        JTextField titleField = new JTextField(20);
        add(titleField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        add(new JLabel("Description:"), gbc);
        gbc.gridx = 1;
        JTextArea descArea = new JTextArea(3, 20);
        add(new JScrollPane(descArea), gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        add(new JLabel("Budget:"), gbc);
        gbc.gridx = 1;
        JTextField budgetField = new JTextField(10);
        add(budgetField, gbc);

        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        JButton postBtn = new JButton("Post Project");
        add(postBtn, gbc);

        gbc.gridy = 5;
        JButton logoutBtn = new JButton("Logout");
        add(logoutBtn, gbc);

        postBtn.addActionListener(e -> {
            String title = titleField.getText();
            String desc = descArea.getText();
            double budget = Double.parseDouble(budgetField.getText());
            String deadline = LocalDate.now().plusDays(30).toString(); // Default 30 days

            if (projectDAO.postProject(title, desc, budget, deadline, client.getUserId())) {
                JOptionPane.showMessageDialog(this, "Project posted for Admin approval!");
                titleField.setText(""); descArea.setText(""); budgetField.setText("");
            }
        });

        logoutBtn.addActionListener(e -> {
            dispose();
            new LoginFrame().setVisible(true);
        });
    }
}
