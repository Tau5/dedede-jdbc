package dedede.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface FromRow<T> {
    T fromRow(ResultSet res) throws SQLException;
}
