package model;

// Base class for all users in the system
// Student, Company, and Admin all extend this class
public class User {

    private String userId;
    private String name;
    private String email;
    private String password;
    private String role; // "STUDENT", "COMPANY", "ADMIN"

    // Constructor
    public User(String userId, String name, String email, String password, String role) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    // Getters and Setters (Encapsulation)
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    // This method can be overridden by child classes (Dynamic Method Dispatch)
    public String getDisplayInfo() {
        return "User ID: " + userId + " | Name: " + name + " | Email: " + email + " | Role: " + role;
    }

    // Convert to file-safe string for storage
    public String toFileString() {
        return userId + "," + name + "," + email + "," + password + "," + role;
    }
}
