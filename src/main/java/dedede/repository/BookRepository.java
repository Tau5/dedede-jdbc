package dedede.repository;

import dedede.domain.Book;
import dedede.infrastructure.CSVManager;
import dedede.infrastructure.CSVRow;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class BookRepository implements IRepositorioExtend<Book, Long> {
    private final CSVManager table;

    public BookRepository(File file) throws IOException {
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            var writer = new BufferedWriter(new FileWriter(file));
            writer.write("id,title,author,borrowed,userId,borrowStart,borrowEnd\n");
            writer.flush();
            writer.close();
        }
        table = new CSVManager(file);
    }

    private Book bookFromRow(CSVRow row) throws InvalidRowException {
        return new Book(
                row.getLong(0).orElseThrow(() -> new InvalidRowException("Error parsing long in column " + 0)),
                row.getString(1).orElseThrow(() -> new InvalidRowException("Error parsing column " + 1 + " as string")),
                row.getString(2).orElseThrow(() -> new InvalidRowException("Error parsing column " + 2 + " as string")),
                row.getBoolean(3).orElseThrow(() -> new InvalidRowException("Error parsing column " + 3 + " as boolean")),
                row.getLong(4).orElseThrow(() -> new InvalidRowException("Error parsing column " + 4 + " as Long")),
                row.getInstant(5).orElseThrow(() -> new InvalidRowException("Error parsing column " + 5 + " as Instant")),
                row.getInstant(6).orElseThrow(() -> new InvalidRowException("Error parsing column " + 6 + " as Instant"))
        );
    }

    @Override
    public long count() {
        return table.listAll().size();
    }

    @Override
    public void deleteById(Long id) {
        table.deleteRow(id.toString(), 0);
        try {
            table.saveFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteAll() {
        table.emptyTable();
    }

    @Override
    public boolean existsById(Long id) {
        var rows = table.listAll().stream().filter(t ->
                t.getLong(0).stream().anyMatch(rowId -> rowId.equals(id))
        );

        var maybeRow = rows.findAny();

        return maybeRow.isPresent();
    }

    @Override
    public Book findById(Long id) {
        var rows = table.listAll().stream().filter(t ->
                t.getLong(0).stream().anyMatch(rowId -> rowId.equals(id))
        );

        var maybeRow = rows.findAny();

        return maybeRow.map(this::bookFromRowHandled).orElse(null);
    }

    @Override
    public Optional<Book> findByIdOptional(Long id) {
        var rows = table.listAll().stream().filter(t ->
                t.getLong(0).stream().anyMatch(rowId -> rowId.equals(id))
        );

        var maybeRow = rows.findAny();

        return maybeRow.map(this::bookFromRowHandled);
    }

    Book bookFromRowHandled(CSVRow row) {
        try {
            return bookFromRow(row);
        } catch (InvalidRowException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Iterable<Book> findAll() {
        // Maps every CSVRow of the table to Book
        return table
                .listAll()
                .stream().map(this::bookFromRowHandled)
                .toList();
    }

    @Override
    public List<Book> findAllList() {
        // Maps every CSVRow of the table to Book
        return table
                .listAll()
                .stream().map(this::bookFromRowHandled)
                .toList();
    }

    private CSVRow bookToRow(Book book) {
        CSVRow row = new CSVRow(7);
        row.setLong(0, book.getID());
        row.setString(1, book.getTitle());
        row.setString(2, book.getAuthor());
        row.setBoolean(3, book.isBorrowed());
        row.setLong(4, book.getUserID());
        row.setInstant(5, book.getBorrowStart());
        row.setInstant(6, book.getBorrowEnd());

        return row;
    }

    @Override
    public <S extends Book> S save(S entity) {
        long newId = 0;

        if (findAllList().isEmpty()) {
            newId = 0;
        } else {
            newId = findAllList().getLast().getID() + 1;
        }

        try {
            if (existsById(entity.getID())) {
                CSVRow row = bookToRow(entity);
                table.updateRow(CSVManager.convertToRaw(entity.getID()), 0, row);
            } else {
                entity.setID(newId);
                CSVRow row = bookToRow(entity);
                table.insertRow(row);
            }

            table.saveFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return entity;
    }
}
