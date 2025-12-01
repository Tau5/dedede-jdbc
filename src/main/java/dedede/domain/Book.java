package dedede.domain;

import java.time.Instant;

public class Book {
    private Long ID;
    private String bookISBN;


    public Book(Long ID, String bookISBN) {
        this.ID = ID;
        this.bookISBN = bookISBN;
    }

    public Book(String bookISBN) {
        this(null, bookISBN);
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getBookISBN() {
        return bookISBN;
    }

    public void setBookISBN(String bookISBN) {
        this.bookISBN = bookISBN;
    }

    @Override
    public String toString() {
        return "Book{" +
                "ID=" + ID +
                ", bookISBN='" + bookISBN + '\'' +
                '}';
    }
}
