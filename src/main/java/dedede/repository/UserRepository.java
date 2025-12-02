package dedede.repository;

import dedede.domain.User;

import javax.xml.transform.Result;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepository implements IRepositorioExtend<User, Long>, ToStatement<User>  {

    private Connection connection;

    public UserRepository(Connection connection) throws IOException {
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

        var query = "SELECT count(*) FROM User;";
        ResultSet rs;
        try {
            var st = connection.createStatement();
            rs = st.executeQuery(query);
            count = (long) rs.getLong(1);
            st.close();
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return count;
    }
    @Override
    public void deleteById(Long id) {
        var query = "DELETE FROM User WHERE id = ?;";
        try {
            var st = connection.prepareStatement(query);
            st.setLong(1, id);
            st.executeQuery(query);
            st.close();
        } catch (SQLException e) {
            throw new RuntimeException("We can't delete usesr with " + id + " because:" + e);
        }
    }

    @Override
    public void deleteAll() {
        var query = "DELETE FROM User;";

        try {
            var st = connection.createStatement();
            st.executeQuery(query);
            st.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean existsById(Long ID) {
        var query = "SELECT * FROM usuario WHERE id = ?;";
        boolean exist = false;
        ResultSet rs;
        try {
            var st = connection.prepareStatement(query);
            st.setLong(1, ID);
            rs = st.executeQuery(query);
            if (rs.getBoolean(String.valueOf(ID))) {
                exist = true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return exist;
    }

    @Override
    public User findById(Long id) throws SQLException {
        var query = "SELECT * FROM usuario WHERE id = ?";
        ResultSet rs;
        User user;
        var st = connection.prepareStatement(query);
        st.setLong(1, id);
        rs = st.executeQuery(query);
        user = userFromRow(rs);
        return user;
    }

    @Override
    public Iterable<User> findAll() throws SQLException {
        List<User> users = new ArrayList<>();
        var query = "SELECT * FROM User;";
        var st = connection.createStatement();
        ResultSet rs = st.executeQuery(query);
        while (rs.next()) {
            users.add(userFromRow(rs));
        }
        return users;
    }

    @Override
    public <S extends User> S save(S user) throws SQLException {
        if (existsById(user.getID())) {
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
        var query = "SELECT * FROM User;";
        var st = connection.createStatement();
        ResultSet rs = st.executeQuery(query);
        while (rs.next()) {
            users.add(userFromRow(rs));
        }
        return users;
    }

    @Override
    public PreparedStatement toUpdate(User user) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("UPDATE User SET name = ?, surname = ? WHERE id = ?;");
        ps.setString(1, user.getName());
        ps.setString(2, user.getSurname());
        ps.setLong(3, user.getID());

        return ps;
    }

    @Override
    public PreparedStatement toInsert(User user) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("INSERT INTO User (name, surname) VALUES (?, ?) RETURNING *;");
        ps.setString(1, user.getName());
        ps.setString(2, user.getSurname());
        return ps;
    }
}
