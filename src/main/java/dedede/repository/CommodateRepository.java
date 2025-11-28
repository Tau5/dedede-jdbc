package dedede.repository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class CommodateRepository implements IRepositorioExtend {
    @Override
    public Optional findByIdOptional(Object o) {
        return Optional.empty();
    }

    @Override
    public List findAllList() throws SQLException {
        return List.of();
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Object o) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public boolean existsById(Object o) {
        return false;
    }

    @Override
    public Object findById(Object o) throws SQLException {
        return null;
    }

    @Override
    public Iterable findAll() throws SQLException {
        return null;
    }

    @Override
    public Object save(Object entity) throws SQLException {
        return null;
    }
}
