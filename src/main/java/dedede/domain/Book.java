package dedede.domain;

import java.time.Instant;

public class Book {
    private Long ID;
    private String title;
    private String author;


    public Book(Long ID, String title, String author) {
        this.title = title;
        this.ID = ID;
        this.author = author;
    }

    public Book(String title, String author) {
        this(null, title, author);
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
