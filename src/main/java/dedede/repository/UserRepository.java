package dedede.repository;

import dedede.domain.User;
import dedede.infrastructure.CSVManager;
import dedede.infrastructure.CSVRow;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class UserRepository implements IRepositorioExtend<User, Long> {

    private CSVManager table;

    public UserRepository(File file) throws IOException {
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            var writer = new BufferedWriter(new FileWriter(file));
            writer.write("id,name,surnames");
            writer.flush();
            writer.close();
        }
        this.table = new CSVManager(file);
    }

    private User userFromRow(CSVRow row) throws InvalidRowException {
        return new User(
                row.getLong(0).orElseThrow(() -> new InvalidRowException("Couldn't parsing column 0 to id")),
                row.getString(1).orElseThrow(() -> new InvalidRowException("Couldn't parsing column 1 to name")),
                row.getString(2).orElseThrow(() -> new InvalidRowException("Couldn't parsing column 2 to surname"))
        );
    }

    private CSVRow userToRow(User user) {
        CSVRow row = new CSVRow(3);
        row.setLong(0, user.getID());
        row.setString(1, user.getName());
        row.setString(2, user.getSurname());
        return row;
    }

    @Override
    public long count() {
        return (long) table.listAll().size();
    }

    @Override
    public void deleteById(Long id) {
        table.deleteRow(id.toString(), 0);
    }

    @Override
    public void deleteAll() {
        table.emptyTable();
    }

    @Override
    public boolean existsById(Long ID) {
        boolean exist = false;
        var rows = table.listAll();

       exist = rows.stream().filter(f -> f.getLong(0).get().equals(ID)).findFirst().isPresent();

       return exist;
    }

    @Override
    public User findById(Long id) {
        var rows = table.listAll();
        var maybeUser = rows.stream().filter(
                f -> f.getLong(0).filter(
                        l -> l.equals(id)).isPresent()
        ).findFirst();
        var user = maybeUser.map(row -> {
            try {
                return userFromRow(row);
            } catch (InvalidRowException e) {
                throw new RuntimeException(e);
            }
        }).get();
        return user;
    }

    @Override
    public Iterable<User> findAll() {
        return table.listAll().stream().map(row -> {
            try {
                return userFromRow(row);
            } catch (InvalidRowException e) {
                throw new RuntimeException(e);
            }
        }).toList();
    }

    @Override
    public <S extends User> S save(S entity) {
        long newId = 0;

        if (findAllList().isEmpty()) {
            newId = 0;
        } else {
            newId = findAllList().getLast().getID() + 1;
        }

        try {
            if(existsById(entity.getID())) {
                CSVRow row = userToRow(entity);
                table.updateRow(CSVManager.convertToRaw(entity.getID()), 0, row);
            } else {
                entity.setID(newId);
                CSVRow row = userToRow(entity);
                table.insertRow(row);
            }

            table.saveFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return entity;
    }

    @Override
    public Optional<User> findByIdOptional(Long aLong) {
        return Optional.empty();
    }

    @Override
    public List<User> findAllList() {
        return (List<User>) this.findAll();
    }
}
