package models;

public class Book {
    private int id;
    private String title;
    private String author;
    private int availableCopies;

    public Book(int id, String title, String author, int availableCopies) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.availableCopies = availableCopies;
    }

    // ✅ Add these getter methods
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getAvailableCopies() {
        return availableCopies;
    }

    @Override
    public String toString() {
        return title + " by " + author + " (" + availableCopies + " available)";
    }
}

