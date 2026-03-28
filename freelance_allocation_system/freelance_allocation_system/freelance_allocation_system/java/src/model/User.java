package model;

public class User {
    private int userId;
    private String username;
    private String password;
    private String role; // ADMIN, CLIENT, STUDENT
    private String email;

    public User(int userId, String username, String password, String role, String email) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.role = role;
        this.email = email;
    }

    // Getters and Setters
    public int getUserId() { return userId; }
    public String getUsername() { return username; }
    public String getRole() { return role; }
    public String getEmail() { return email; }
}
