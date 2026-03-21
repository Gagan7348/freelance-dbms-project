package gui;

import dao.UserDAO;
import model.User;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends JFrame {
    private JTextField userField;
    private JPasswordField passField;
    private UserDAO userDAO;

    public LoginFrame() {
        userDAO = new UserDAO();
        setTitle("Freelance System - Login");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("Username:"), gbc);
        gbc.gridx = 1;
        userField = new JTextField(15);
        add(userField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        passField = new JPasswordField(15);
        add(passField, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        JButton loginBtn = new JButton("Login");
        add(loginBtn, gbc);

        gbc.gridy = 3;
        JButton regBtn = new JButton("Don't have an account? Register");
        add(regBtn, gbc);

        loginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            }
        });

        regBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showRegisterDialog();
            }
        });
    }

    private void handleLogin() {
        String username = userField.getText();
        String password = new String(passField.getPassword());
        User user = userDAO.login(username, password);
        if (user != null) {
            JOptionPane.showMessageDialog(this, "Welcome " + user.getUsername());
            dispose();
            openDashboard(user);
        } else {
            JOptionPane.showMessageDialog(this, "Invalid credentials", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void openDashboard(User user) {
        if (user.getRole().equals("ADMIN")) {
            new AdminDashboard(user).setVisible(true);
        } else if (user.getRole().equals("CLIENT")) {
            new ClientDashboard(user).setVisible(true);
        } else if (user.getRole().equals("STUDENT")) {
            new StudentDashboard(user).setVisible(true);
        }
    }

    private void showRegisterDialog() {
        // Simple registration dialog (omitted for brevity in this initial step)
        String username = JOptionPane.showInputDialog(this, "Enter Username:");
        String password = JOptionPane.showInputDialog(this, "Enter Password:");
        String email = JOptionPane.showInputDialog(this, "Enter Email:");
        String[] roles = {"CLIENT", "STUDENT"};
        String role = (String) JOptionPane.showInputDialog(this, "Select Role:", "Role", 
                JOptionPane.QUESTION_MESSAGE, null, roles, roles[0]);

        if (userDAO.registerUser(username, password, role, email)) {
            JOptionPane.showMessageDialog(this, "Registration Successful!");
        } else {
            JOptionPane.showMessageDialog(this, "Registration Failed!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}
