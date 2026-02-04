package dedede.services;

import dedede.domain.Book;
import dedede.domain.CatalogBook;
import dedede.domain.Commodate;
import dedede.domain.User;
import dedede.repository.BookRepository;
import dedede.repository.CatalogBookRepository;
import dedede.repository.CommodateRepository;
import dedede.view.Model;

import java.sql.SQLException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAmount;
import java.util.List;

public class CommodateService {
    CommodateRepository commodateRepository;
    CatalogBookRepository catalogBookRepository;
    BookRepository bookRepository;

    public CommodateService(Model model) {
        this.commodateRepository = model.commodates;
        this.catalogBookRepository = model.catalog;
        this.bookRepository = model.books;
    }

    public List<Commodate> getCommodatesForBook(Book book) {
        try {
            return commodateRepository
                    .findAllList()
                    .stream()
                    .filter(c -> c.getBookID() == book.getID())
                    .toList();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Commodate registerCommodate(Book book, User user) throws SQLException {
        return commodateRepository.save(
                new Commodate(-1, Instant.now(), Instant.now().plus(15, ChronoUnit.DAYS), user.getID(), book.getID())
        );
    }

    public List<Commodate> getCommodatesForUser(User user) throws SQLException {
        return commodateRepository.findAllList()
                .stream()
                .filter(c -> c.getUserID() == user.getID())
                .toList();
    }

    public Book getBookForCommodate(Commodate commodate) throws SQLException {
        return bookRepository.findById(commodate.getBookID());
    }

    public CatalogBook getCatalogBookForCommodate(Commodate commodate) throws SQLException {
        return catalogBookRepository.findById(
                getBookForCommodate(commodate).getBookISBN()
        );
    }


}
