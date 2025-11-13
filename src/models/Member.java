package models;

import java.sql.Date;

public class Member {
    private int memberId;
    private String name;
    private String department;
    private String email;
    private long phone;
    private Date joinDate;
    private String membership;

    public Member(int memberId, String name, String department, String email, long phone, Date joinDate, String membership) {
        this.memberId = memberId;
        this.name = name;
        this.department = department;
        this.email = email;
        this.phone = phone;
        this.joinDate = joinDate;
        this.membership = membership;
    }

    // Getters and setters
    public int getMemberId() { return memberId; }
    public void setMemberId(int memberId) { this.memberId = memberId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public long getPhone() { return phone; }
    public void setPhone(long phone) { this.phone = phone; }

    public Date getJoinDate() { return joinDate; }
    public void setJoinDate(Date joinDate) { this.joinDate = joinDate; }

    public String getMembership() { return membership; }
    public void setMembership(String membership) { this.membership = membership; }

    @Override
    public String toString() {
        return memberId + " - " + name + " (" + membership + ")";
    }

    public void setPassword(String password) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setPassword'");
    }
}

