package dedede.repository;

import dedede.domain.Book;
import javax.sql.RowSet;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookRepository implements IRepositorioExtend<Book, Long> {
    private final Connection conn;

    public BookRepository(Connection conn) throws IOException {
        this.conn = conn;
    }

    private Book bookFromRow(ResultSet row) throws SQLException {
        return new Book(
                row.getLong(1),
                row.getString(2),
                row.getString(3)
        );
    }

    @Override
    public long count() throws SQLException {
        var statement = conn.createStatement();
        var response = statement.executeQuery("select count(*) from book;");

        var count = response.getLong(1);

        statement.close();
        return count;
    }

    @Override
    public void deleteById(Long id) throws SQLException {
        var statement = conn.prepareStatement("DELETE FROM book where id = ?;");
        statement.setLong(1, id);
        statement.execute();
        statement.close();
    }

    @Override
    public void deleteAll() throws SQLException {
        var statement = conn.createStatement();
        statement.execute("DELETE FROM book;");
        statement.close();
    }

    @Override
    public boolean existsById(Long id) throws SQLException {
        var statement = conn.prepareStatement("SELECT count(*) from book where id = ?;");
        statement.setLong(1, id);
        var res = statement.executeQuery();
        var answer = res.getLong(1) > 0;
        statement.close();

        return answer;
    }

    @Override
    public Book findById(Long id) throws SQLException {
        var statement = conn.prepareStatement("SELECT * from book where id = ?;");
        var res = statement.executeQuery();

        Book book = bookFromRow(res);

        statement.close();
        return book;
    }

    @Override
    public Optional<Book> findByIdOptional(Long id) throws SQLException {
        var statement = conn.prepareStatement("SELECT * from book where id = ?;");
        statement.setLong(0, id);
        var res = statement.executeQuery();
        Optional<Book> out = Optional.empty();
        if (res.getRow() > 0) {
            out = Optional.of(bookFromRow(res));
        }

        statement.close();

        return out;
    }

    @Override
    public Iterable<Book> findAll() throws SQLException {
        // Maps every CSVRow of the table to Book
        var statement = conn.createStatement();
        var res = statement.executeQuery("select * from book;");
        ArrayList<Book> books = new ArrayList<>();
        while (res.next()) {
            books.add(this.bookFromRow(res));
        }

        return books;
    }

    @Override
    public List<Book> findAllList() throws SQLException {
        // Maps every CSVRow of the table to Book
        return (List<Book>) findAll();
    }

    private void bookToRow(PreparedStatement row, Book book) throws SQLException {
        row.setString(0, book.getTitle());
        row.setString(1, book.getAuthor());
    }

    @Override
    public <S extends Book> S save(S entity) throws SQLException {
        if (existsById(entity.getID())) {
            String sql = "UPDATE public.book SET " +
                    "title = ?, " +
                    "author = ?, " +
                    "WHERE id = ?";

            PreparedStatement statement = conn.prepareStatement(sql);
            bookToRow(statement, entity);
            statement.setLong(2, entity.getID());
            statement.executeUpdate();
            statement.close();
            return entity;
        } else {
            var statement = conn.prepareStatement(
                    """
                        INSERT INTO book (
                        	title,
                        	author,
                        )
                        VALUES (?, ?) returning *;
                    """
            );

            bookToRow(statement, entity);
            var res = statement.executeQuery();
            var book = (S) bookFromRow(res);
            statement.close();
            return book;
        }
    }
}
