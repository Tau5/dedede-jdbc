package dedede.domain;

import java.time.Instant;

public class Commodate {
    private long id;
    private Instant issueDate;
    private Instant periodEnd;
    private long userID;
    private long bookID;

    public Commodate(long id, Instant issueDate, Instant periodEnd, long userID, long bookID) {
        this.id = id;
        this.issueDate = issueDate;
        this.periodEnd = periodEnd;
        this.userID = userID;
        this.bookID = bookID;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Instant getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Instant issueDate) {
        this.issueDate = issueDate;
    }

    public Instant getPeriodEnd() {
        return periodEnd;
    }

    public void setPeriodEnd(Instant periodEnd) {
        this.periodEnd = periodEnd;
    }

    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    public long getBookID() {
        return bookID;
    }

    public void setBookID(long bookID) {
        this.bookID = bookID;
    }
}
