package dedede.repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface ToStatement<T> {
    PreparedStatement toUpdate(T t) throws SQLException;
    PreparedStatement toInsert(T t) throws SQLException;
}
