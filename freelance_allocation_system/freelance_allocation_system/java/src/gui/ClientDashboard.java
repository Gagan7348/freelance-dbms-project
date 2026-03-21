package gui;

import dao.ProjectDAO;
import model.User;
import javax.swing.*;
import java.awt.*;

public class ClientDashboard extends JFrame {

private ProjectDAO projectDAO;
private User client;

public ClientDashboard(User client) {

    this.client = client;
    this.projectDAO = new ProjectDAO();

    setTitle("Client Dashboard - " + client.getUsername());
    setSize(500, 450);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    setLayout(new GridBagLayout());

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(10,10,10,10);

    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.gridwidth = 2;

    add(new JLabel("Welcome " + client.getUsername() + "! Post a new project below:"), gbc);

    gbc.gridwidth = 1;

    // Title
    gbc.gridx = 0;
    gbc.gridy = 1;
    add(new JLabel("Title:"), gbc);

    gbc.gridx = 1;
    JTextField titleField = new JTextField(20);
    add(titleField, gbc);

    // Description
    gbc.gridx = 0;
    gbc.gridy = 2;
    add(new JLabel("Description:"), gbc);

    gbc.gridx = 1;
    JTextArea descArea = new JTextArea(3,20);
    add(new JScrollPane(descArea), gbc);

    // Budget
    gbc.gridx = 0;
    gbc.gridy = 3;
    add(new JLabel("Budget:"), gbc);

    gbc.gridx = 1;
    JTextField budgetField = new JTextField(10);
    add(budgetField, gbc);

    // Deadline
    gbc.gridx = 0;
    gbc.gridy = 4;
    add(new JLabel("Deadline (YYYY-MM-DD):"), gbc);

    gbc.gridx = 1;
    JTextField deadlineField = new JTextField(10);
    add(deadlineField, gbc);

    // Post Project Button
    gbc.gridx = 0;
    gbc.gridy = 5;
    gbc.gridwidth = 2;

    JButton postBtn = new JButton("Post Project");
    add(postBtn, gbc);

    // Logout Button
    gbc.gridy = 6;

    JButton logoutBtn = new JButton("Logout");
    add(logoutBtn, gbc);

    postBtn.addActionListener(e -> {

        String title = titleField.getText();
        String desc = descArea.getText();
        String deadline = deadlineField.getText();

        double budget;

        try {
            budget = Double.parseDouble(budgetField.getText());
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(this,"Enter valid budget");
            return;
        }

        if(title.isEmpty() || desc.isEmpty() || deadline.isEmpty()){
            JOptionPane.showMessageDialog(this,"Please fill all fields");
            return;
        }

        if(projectDAO.postProject(title, desc, budget, deadline, client.getUserId())){

            JOptionPane.showMessageDialog(this,"Project posted for Admin approval!");

            titleField.setText("");
            descArea.setText("");
            budgetField.setText("");
            deadlineField.setText("");
        }

    });

    logoutBtn.addActionListener(e -> {

        dispose();
        new LoginFrame().setVisible(true);

    });

}
}
