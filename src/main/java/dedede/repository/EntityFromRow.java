package dedede.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface FromRow<T> {
    /**
     * Given a ResultSet row from a SELECT statement which returns ALL rows
     * of the table, returns a new Entity
     *
     * @param res Result set from a SELECT * from Entity
     * @return T Entity
     * @throws SQLException
     */
    T fromRow(ResultSet res) throws SQLException;
}
