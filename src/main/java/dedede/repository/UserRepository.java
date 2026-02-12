package dedede.repository;

import dedede.domain.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepository implements IRepositorioExtend<User, Long>, ToStatement<User>  {
    static String table_name = "public.\"User\"";
    private Connection connection;

    public UserRepository(Connection connection) {
        this.connection = connection;
    }

    private User userFromRow(ResultSet rs) throws SQLException {
        return new User(
                rs.getLong(1),
                rs.getString(2),
                rs.getString(3)
        );
    }

    @Override
    public long count() {

        long count = 0;

        var query = "SELECT count(*) FROM" + this.table_name + ";";
        ResultSet rs;
        try {
            var st = connection.createStatement();
            rs = st.executeQuery(query);
            rs.next();
            count = (long) rs.getLong(1);
            st.close();
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return count;
    }

    @Override
    public void deleteById(Long id) throws SQLException {
        var query = "DELETE FROM" + this.table_name + " WHERE id = ?;";
        var st = connection.prepareStatement(query);
        st.setLong(1, id);
        st.executeQuery();
        st.close();
    }

    @Override
    public void deleteAll() {
        var query = "DELETE FROM" + this.table_name + ";";

        try {
            var st = connection.createStatement();
            st.executeQuery(query);
            st.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean existsById(Long ID) throws SQLException {
        var statement = connection.prepareStatement("SELECT count(*) from " + this.table_name + " where id = ?;");
        statement.setLong(1, ID);
        var res = statement.executeQuery();
        res.next();
        var answer = res.getLong(1) > 0;
        statement.close();

        return answer;
    }

    @Override
    public User findById(Long id) throws SQLException {
        var query = "SELECT * FROM " + this.table_name + " WHERE id = ?";
        ResultSet rs;
        User user;
        var st = connection.prepareStatement(query);
        st.setLong(1, id);
        rs = st.executeQuery();
        rs.next();
        user = userFromRow(rs);
        return user;
    }

    @Override
    public Iterable<User> findAll() throws SQLException {
        List<User> users = new ArrayList<>();
        var query = "SELECT * FROM " + this.table_name + ";";
        var st = connection.createStatement();
        ResultSet rs = st.executeQuery(query);
        while (rs.next()) {
            users.add(userFromRow(rs));
        }
        return users;
    }

    @Override
    public <S extends User> S save(S user) throws SQLException {
        if (user.getID() != null && existsById(user.getID())) {
            var stUpdate = toUpdate(user);
            stUpdate.executeUpdate();
            stUpdate.close();
            return user;
        } else {
            var stInsert = toInsert(user);
            stInsert.executeQuery();
            stInsert.close();
            return user;
        }
    }

    @Override
    public Optional<User> findByIdOptional(Long aLong) {
        return Optional.empty();
    }

    @Override
    public List<User> findAllList() throws SQLException {
        List<User> users = new ArrayList<>();
        var query = "SELECT * FROM " + this.table_name + ";";
        var st = connection.createStatement();
        ResultSet rs = st.executeQuery(query);
        while (rs.next()) {
            users.add(userFromRow(rs));
        }
        return users;
    }

    @Override
    public PreparedStatement toUpdate(User user) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("UPDATE " + this.table_name + " SET name = ?, surname = ? WHERE id = ?;");
        ps.setString(1, user.getName());
        ps.setString(2, user.getSurname());
        ps.setLong(3, user.getID());

        return ps;
    }

    @Override
    public PreparedStatement toInsert(User user) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("INSERT INTO " + this.table_name + " (name, surname) VALUES (?, ?) RETURNING *;");
        ps.setString(1, user.getName());
        ps.setString(2, user.getSurname());
        return ps;
    }
}
