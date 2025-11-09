package models;

public class Staff {
    private int staffId;
    private String name;
    private String role;
    private String username;
    private String password;

    public Staff(int staffId, String name, String role, String username, String password) {
        this.staffId = staffId;
        this.name = name;
        this.role = role;
        this.username = username;
        this.password = password;
    }

    // Getters and setters
    public int getStaffId() { return staffId; }
    public void setStaffId(int staffId) { this.staffId = staffId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    @Override
    public String toString() {
        return staffId + " - " + name + " (" + role + ")";
    }
}

