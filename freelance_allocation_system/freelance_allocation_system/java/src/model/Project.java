package model;

import java.util.Date;

public class Project {
    private int projectId;
    private String title;
    private String description;
    private double budget;
    private Date deadline;
    private int clientId;
    private String status; // PENDING, APPROVED, REJECTED, ASSIGNED, COMPLETED
    private String rejectionReason;

    public Project(int projectId, String title, String description, double budget, Date deadline, int clientId, String status) {
        this.projectId = projectId;
        this.title = title;
        this.description = description;
        this.budget = budget;
        this.deadline = deadline;
        this.clientId = clientId;
        this.status = status;
    }

    // Getters and Setters
    public int getProjectId() { return projectId; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public double getBudget() { return budget; }
    public Date getDeadline() { return deadline; }
    public int getClientId() { return clientId; }
    public String getStatus() { return status; }
    public String getRejectionReason() { return rejectionReason; }

    public void setStatus(String status) { this.status = status; }
    public void setRejectionReason(String reason) { this.rejectionReason = reason; }
}
