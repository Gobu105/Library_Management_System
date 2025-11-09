package models;

public class Fine {
    private int fineId;
    private int memberId;
    private int bookId;
    private double amount;
    private String status;

    public Fine(int fineId, int memberId, int bookId, double amount, String status) {
        this.fineId = fineId;
        this.memberId = memberId;
        this.bookId = bookId;
        this.amount = amount;
        this.status = status;
    }

    // Getters and setters
    public int getFineId() { return fineId; }
    public void setFineId(int fineId) { this.fineId = fineId; }

    public int getMemberId() { return memberId; }
    public void setMemberId(int memberId) { this.memberId = memberId; }

    public int getBookId() { return bookId; }
    public void setBookId(int bookId) { this.bookId = bookId; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return "Fine#" + fineId + " - " + amount + " (" + status + ")";
    }
}

