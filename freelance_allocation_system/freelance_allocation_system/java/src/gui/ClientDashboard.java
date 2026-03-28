package gui;

import dao.ProjectDAO;
import model.Project;
import model.User;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ClientDashboard extends JFrame {
    private ProjectDAO projectDAO;
    private User client;
    private JTextField selectedDateField;

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
        JPanel datePanel = createDatePanel();
        panel.add(datePanel, gbc);

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
                String deadline = selectedDateField.getText();

                if (title.isEmpty() || desc.isEmpty() || deadline.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please fill in all fields!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (projectDAO.postProject(title, desc, budget, deadline, client.getUserId())) {
                    JOptionPane.showMessageDialog(this, "Project posted for Admin approval!");
                    titleField.setText("");
                    descArea.setText("");
                    budgetField.setText("");
                    selectedDateField.setText("");
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

    private JPanel createDatePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // Create date display field
        selectedDateField = new JTextField(20);
        selectedDateField.setEditable(false);
        selectedDateField.setToolTipText("Click 'Select Date' to choose deadline");
        
        // Create calendar button
        JButton calendarButton = new JButton("📅 Select Date");
        calendarButton.addActionListener(e -> showCalendarDialog());
        
        panel.add(selectedDateField, BorderLayout.CENTER);
        panel.add(calendarButton, BorderLayout.EAST);
        
        return panel;
    }

    private void showCalendarDialog() {
        JDialog calendarDialog = new JDialog(this, "Select Deadline Date", true);
        calendarDialog.setSize(300, 250);
        calendarDialog.setLocationRelativeTo(this);
        
        JPanel panel = new JPanel(new BorderLayout());
        
        // Month/Year controls
        JPanel monthYearPanel = new JPanel(new FlowLayout());
        
        // Month combo box
        String[] months = {"January", "February", "March", "April", "May", "June", 
                         "July", "August", "September", "October", "November", "December"};
        JComboBox<String> monthCombo = new JComboBox<>(months);
        monthCombo.setSelectedIndex(LocalDate.now().getMonthValue() - 1);
        
        // Year spinner
        SpinnerNumberModel yearModel = new SpinnerNumberModel(LocalDate.now().getYear(), 
                                                      LocalDate.now().getYear(), 
                                                      LocalDate.now().getYear() + 5, 1);
        JSpinner yearSpinner = new JSpinner(yearModel);
        
        monthYearPanel.add(monthCombo);
        monthYearPanel.add(yearSpinner);
        
        // Calendar grid
        JPanel calendarPanel = new JPanel(new GridLayout(7, 7, 2, 2));
        
        // Day headers
        String[] dayHeaders = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        for (String header : dayHeaders) {
            JLabel label = new JLabel(header, SwingConstants.CENTER);
            label.setFont(label.getFont().deriveFont(Font.BOLD));
            calendarPanel.add(label);
        }
        
        // Day buttons
        JButton[] dayButtons = new JButton[42];
        LocalDate currentDate = LocalDate.now();
        
        for (int i = 0; i < 42; i++) {
            dayButtons[i] = new JButton("");
            dayButtons[i].setMargin(new Insets(2, 2, 2, 2));
            dayButtons[i].setFont(new Font("Arial", Font.PLAIN, 10));
            
            final int dayIndex = i;
            dayButtons[i].addActionListener(e -> {
                String dayText = dayButtons[dayIndex].getText();
                if (!dayText.isEmpty()) {
                    int selectedMonth = monthCombo.getSelectedIndex() + 1;
                    int selectedYear = (Integer) yearSpinner.getValue();
                    int selectedDay = Integer.parseInt(dayText);
                    
                    LocalDate selectedDate = LocalDate.of(selectedYear, selectedMonth, selectedDay);
                    selectedDateField.setText(selectedDate.format(DateTimeFormatter.ISO_LOCAL_DATE));
                    calendarDialog.dispose();
                }
            });
            
            calendarPanel.add(dayButtons[i]);
        }
        
        // Update calendar when month/year changes
        ActionListener updateCalendar = e -> updateCalendarGrid(dayButtons, 
                                                          monthCombo.getSelectedIndex() + 1, 
                                                          (Integer) yearSpinner.getValue(), 
                                                          currentDate);
        
        monthCombo.addActionListener(updateCalendar);
        yearSpinner.addChangeListener(e -> updateCalendar.actionPerformed(null));
        
        // Initial calendar update
        updateCalendarGrid(dayButtons, currentDate.getMonthValue(), currentDate.getYear(), currentDate);
        
        // Buttons panel
        JPanel buttonPanel = new JPanel();
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> calendarDialog.dispose());
        buttonPanel.add(cancelButton);
        
        panel.add(monthYearPanel, BorderLayout.NORTH);
        panel.add(calendarPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        calendarDialog.add(panel);
        calendarDialog.setVisible(true);
    }
    
    private void updateCalendarGrid(JButton[] dayButtons, int month, int year, LocalDate today) {
        LocalDate firstOfMonth = LocalDate.of(year, month, 1);
        int firstDayOfWeek = firstOfMonth.getDayOfWeek().getValue() % 7; // Adjust for Sunday=0
        int daysInMonth = firstOfMonth.lengthOfMonth();
        
        // Clear all buttons
        for (JButton button : dayButtons) {
            button.setText("");
            button.setEnabled(true);
            button.setBackground(null);
        }
        
        // Set day numbers
        for (int day = 1; day <= daysInMonth; day++) {
            int buttonIndex = firstDayOfWeek + day - 1;
            if (buttonIndex < 42) {
                dayButtons[buttonIndex].setText(String.valueOf(day));
                
                // Highlight today
                LocalDate currentDate = LocalDate.of(year, month, day);
                if (currentDate.equals(today)) {
                    dayButtons[buttonIndex].setBackground(new Color(173, 216, 230));
                }
                
                // Disable past dates
                if (currentDate.isBefore(today)) {
                    dayButtons[buttonIndex].setEnabled(false);
                    dayButtons[buttonIndex].setBackground(Color.LIGHT_GRAY);
                }
            }
        }
        
        // Disable empty buttons
        for (int i = 0; i < firstDayOfWeek; i++) {
            dayButtons[i].setEnabled(false);
        }
        
        for (int i = firstDayOfWeek + daysInMonth; i < 42; i++) {
            dayButtons[i].setEnabled(false);
        }
    }
}
