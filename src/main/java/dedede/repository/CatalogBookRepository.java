package dedede.repository;

import dedede.domain.CatalogBook;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CatalogBookRepository implements IRepositorioExtend<CatalogBook, String>, FromRow<CatalogBook>, ToStatement<CatalogBook> {
    private final Connection conn;

    public CatalogBookRepository(Connection conn) throws IOException {
        this.conn = conn;
    }

    @Override
    public Optional<CatalogBook> findByIdOptional(String id) throws SQLException {
        try {
            return Optional.of(findById(id));
        } catch (SQLException e) {
            return Optional.empty();
        }
    }

    @Override
    public void deleteById(String id) throws SQLException {
        var p = conn.prepareStatement("delete from catalogbook where id = ?;");
        p.setString(1, id);
        p.executeQuery();
    }

    @Override
    public void deleteAll() throws SQLException {
        var p = conn.prepareStatement("delete from catalogbook;");
        p.executeQuery();
    }

    @Override
    public boolean existsById(String id) throws SQLException {
        var p = conn.prepareStatement("select count(*) from CatalogBook where id = ?;");
        p.setString(1, id);
        return p.executeQuery().getLong(1) > 0;
    }

    @Override
    public CatalogBook findById(String id) throws SQLException {
        var p = conn.prepareStatement("select * from CatalogBook where id = ?;");
        p.setString(1, id);
        return fromRow(p.executeQuery());
    }

    @Override
    public Iterable<CatalogBook> findAll() throws SQLException {
        return findAllList();
    }

    @Override
    public <S extends CatalogBook> S save(S entity) throws SQLException {
        if (entity != null && existsById(entity.getISBN())) {
            var s = toUpdate(entity);
            s.executeQuery();
            return entity;
        } else {
            var s = toInsert(entity);
            //noinspection unchecked
            return (S) fromRow(s.executeQuery());
        }
    }

    @Override
    public List findAllList() throws SQLException {
        var statement = conn.createStatement();
        var res = statement.executeQuery("select * from CatalogBook;");
        ArrayList<CatalogBook> cbooks = new ArrayList<>();
        while (res.next()) {
            cbooks.add(fromRow(res));
        }

        return cbooks;
    }

    @Override
    public long count() throws SQLException {
        return conn.createStatement()
                .executeQuery("select count(*) from CatalogBook;")
                .getLong(1);
    }

    @Override
    public CatalogBook fromRow(ResultSet res) throws SQLException {
        return new CatalogBook(
                res.getString(0),
                res.getString(1),
                res.getString(2)
        );
    }

    @Override
    public PreparedStatement toUpdate(CatalogBook catalogBook) throws SQLException {
        var p = conn.prepareStatement("UPDATE CatalogBook SET" +
                "title = ?" +
                "author = ?" +
                "WHERE id = ?;");
        p.setString(0, catalogBook.getTitle());
        p.setString(1, catalogBook.getAuthor());
        p.setString(2, catalogBook.getISBN());

        return p;
    }

    @Override
    public PreparedStatement toInsert(CatalogBook catalogBook) throws SQLException {
        var p = conn.prepareStatement("INSERT INTO CatalogBook (title, author)" +
                "VALUES (?, ?) returning *;");

        p.setString(0, catalogBook.getTitle());
        p.setString(1, catalogBook.getAuthor());

        return p;
    }
}
