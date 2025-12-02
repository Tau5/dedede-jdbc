package dedede.repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Provides methods to prepare staments to update and insert
 * rows from an Entity
 *
 * @param <T> Entity
 */
public interface ToStatement<T> {
    /**
     * Returns a PreparedStatement to update all columns of row which
     * corresponds to the id of the given entity (except ID)
     *
     * @param t Entity
     * @return PreparedStatement to update
     * @throws SQLException
     */
    PreparedStatement toUpdate(T t) throws SQLException;

    /**
     * Returns a PreparedStatement to insert a new row with the values of the given
     * entity
     *
     * @param t Entity
     * @return PreparedStatemtn to insert new row
     * @throws SQLException
     */
    PreparedStatement toInsert(T t) throws SQLException;
}
