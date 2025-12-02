package dedede.repository;

import dedede.domain.Commodate;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CommodateRepository implements IRepositorioExtend<Commodate, Long>, ToStatement<Commodate>, FromRow<Commodate
        > {

    private Connection connection;

    public CommodateRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Optional<Commodate> findByIdOptional(Long aLong) throws SQLException {
        return Optional.empty();
    }

    @Override
    public List<Commodate> findAllList() throws SQLException {
        List<Commodate> commodates = new ArrayList<>();
        var query = "SELECT * FROM Commodate;";
        ResultSet rs;
        try(PreparedStatement st = connection.prepareStatement(query)) {
            rs = st.executeQuery();
            while (rs.next()) {
                commodates.add(fromRow(rs));
            }
        }
        return commodates;
    }

    @Override
    public long count() throws SQLException {
        long count = 0;
        var query = "SELECT count(*) FROM Commodate;";
        ResultSet rs;
        try (PreparedStatement st = connection.prepareStatement(query)) {
            rs = st.executeQuery();
            count = rs.getLong(1);
            rs.close();
        }
        return count;
    }

    @Override
    public void deleteById(Long id) throws SQLException {
        var query = "DELETE FROM Commodate WHERE id = ?;";

        try(PreparedStatement st = connection.prepareStatement(query)) {
            st.setLong(1, id);
            st.executeQuery();
        }
    }

    @Override
    public void deleteAll() throws SQLException {
        var query = "DELETE FROM Commodate;";
        try (PreparedStatement st = connection.prepareStatement(query)) {
            st.executeQuery();
        }
    }

    @Override
    public boolean existsById(Long id) throws SQLException {
        var query = "SELECT * FROM Commodate WHERE id = ?;";
        boolean exist = false;
        ResultSet rs;
        try(PreparedStatement st = connection.prepareStatement(query)) {
            st.setLong(1, id);
             rs = st.executeQuery();
             if (rs.getBoolean(String.valueOf(id))) {
                 exist = true;
             }
             rs.close();
        }
        return exist;
    }

    @Override
    public Commodate findById(Long id) throws SQLException {
        var query = "SELECT * FROM Commodate WHERE id = ?;";
        Commodate commodate;
        ResultSet rs;
        try(PreparedStatement st = connection.prepareStatement(query)) {
            st.setLong(1, id);
            rs = st.executeQuery();
            commodate = fromRow(rs);
        }
        rs.close();
        return commodate;
    }

    @Override
    public Iterable<Commodate> findAll() throws SQLException {
        return null;
    }

    @Override
    public <S extends Commodate> S save(S entity) throws SQLException {
        if (existsById(entity.getId())) {
            var ps = toUpdate(entity);
            ps.executeUpdate();
            ps.close();
            return entity;
        } else {
            var ps = toInsert(entity);
            ps.executeQuery();
            ps.close();
            return entity;
        }
    }


    @Override
    public PreparedStatement toUpdate(Commodate commodate) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("UPDATE Commodate SET issue_date = ?, period_end = ?, user_ID = ?, book_ID = ? WHERE id = ?;");
        ps.setDate(1, (Date) Date.from(commodate.getIssueDate()));
        ps.setDate(2, (Date) Date.from(commodate.getPeriodEnd()));
        ps.setLong(3, commodate.getUserID());
        ps.setLong(4, commodate.getBookID());
        return ps;
    }

    @Override
    public PreparedStatement toInsert(Commodate commodate) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("INSERT INTO Commodate (issue_date, period_end, user_id, book_ID) VALUES(?, ?, ?, ?);");
        ps.setDate(1, (Date) Date.from(commodate.getIssueDate()));
        ps.setDate(2, (Date) Date.from(commodate.getPeriodEnd()));
        ps.setLong(3, commodate.getUserID());
        ps.setLong(4, commodate.getBookID());
        return ps;
    }

    @Override
    public Commodate fromRow(ResultSet res) throws SQLException {
        return new Commodate(
                res.getLong(1),
                res.getDate(2).toInstant(),
                res.getDate(3).toInstant(),
                res.getLong(4),
                res.getLong(5)
        );
    }
}
