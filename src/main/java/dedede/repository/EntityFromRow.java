package dedede.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface EntityFromRow<T> {
    T fromRow(ResultSet res) throws SQLException;
}
