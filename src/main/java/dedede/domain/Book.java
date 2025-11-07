package dedede.domain;

import java.time.Duration;
import java.time.Instant;

public class Book {
    private Long ID;
    private String title;
    private String author;
    private Boolean borrowed;
    private Long userID;
    private Instant borrowStart;
    private Instant borrowEnd;


    public Book(Long ID, String title, String author, boolean borrowed, long userID, Instant borrowStart, Instant borrowEnd) {
        this.title = title;
        this.ID = ID;
        this.author = author;
        this.borrowed = borrowed;
        this.borrowStart = borrowStart;
        this.borrowEnd = borrowEnd;
        this.userID = userID;
    }

    public Book(String title, String author, boolean borrowed, long userID, Instant borrowStart, Instant borrowEnd) {
        this(null, title, author, borrowed, userID, borrowStart, borrowEnd);
    }

    public Book(String title, String author) {
        this(title, author, false, 0, Instant.ofEpochSecond(0), Instant.ofEpochSecond(0));
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public boolean isBorrowed() {
        return borrowed;
    }

    public void setBorrowed(Boolean borrowed) {
        this.borrowed = borrowed;
    }

    public Instant getBorrowEnd() {
        return borrowEnd;
    }

    public void setBorrowEnd(Instant borrowEnd) {
        this.borrowEnd = borrowEnd;
    }

    public Instant getBorrowStart() {
        return borrowStart;
    }

    public void setBorrowStart(Instant borrowStart) {
        this.borrowStart = borrowStart;
    }

    public void borrow(User user) {
        this.setBorrowed(true);
        this.setUserID(this.ID);
        this.setBorrowStart(Instant.now());
        this.setBorrowEnd(
                Instant
                .now()
                .plus(Duration.ofDays(15))
        );
    }

    public void returnBook() {
        this.setBorrowed(false);
        this.setBorrowStart(null);
        this.setBorrowEnd(null);
    }

    @Override
    public String toString() {
        return "Book{" +
                "ID=" + ID +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", borrowed=" + borrowed +
                ", userID=" + userID +
                ", borrowStart=" + borrowStart +
                ", borrowEnd=" + borrowEnd +
                '}';
    }
}
