package model;

public class User {

    private String userId;
    private String name;
    private String email;
    private String password;
    private String role; 

    public User(String uId, String n, String em, String p, String r ) {
        userId = uId;
        name = n;
        email = em;
        password = p;
        role = r;
    }

    //(Encapsulation)
    public String getUserId() {
        return userId;
    }

    public void setUserId(String uId) {
        userId = uId;
    }

    public String getName() {
        return name;
    }

    public void setName(String n) {
        name = n;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String em ) {
        email = em;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String p) {
        password = p;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String r) {
        role = r;
    }

    //(Dynamic Method Dispatch)
    public String getDisplayInfo() {
        return "User ID: " + userId + " | Name: " + name + " | Email: " + email + " | Role: " + role;
    }

    //Convert to file-safe string for storage
    public String toFileString() {
        return userId + "," + name + "," + email + "," + password + "," + role;
    }
}

