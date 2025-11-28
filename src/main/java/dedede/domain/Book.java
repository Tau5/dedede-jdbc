package dedede.domain;

import java.time.Duration;
import java.time.Instant;

public class Book {
    private Long ID;
    private String title;
    private String author;


    public Book(Long ID, String title, String author, boolean borrowed, long userID, Instant borrowStart, Instant borrowEnd) {
        this.title = title;
        this.ID = ID;
        this.author = author;
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

    @Override
    public String toString() {
        return "Book{" +
                "ID=" + ID +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                '}';
    }
}
