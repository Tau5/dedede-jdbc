package dedede.services;

import dedede.domain.Book;
import dedede.domain.CatalogBook;
import dedede.domain.Commodate;
import dedede.domain.User;
import dedede.repository.BookRepository;
import dedede.view.Model;

import java.sql.SQLException;
import java.util.List;

public class BookService {
    CommodateService commodateService;
    BookRepository bookRepository;

    public BookService(Model model) {
       this.commodateService = new CommodateService(model);
       this.bookRepository = model.books;
    }

    public boolean isBookBorrowed(Book book) {
        return commodateService.getCommodatesForBook(book).stream().filter(Commodate::isActive).findFirst().isPresent();
    }

    public Commodate borrowBook(Book book, User user) throws SQLException, BookAlreadyBorrowedException {
        if (isBookBorrowed(book)) {
            throw new BookAlreadyBorrowedException("Esta copia ya está prestada");
        } else if (userHasAlreadyBorrowedACopy(book, user)) {
            throw new BookAlreadyBorrowedException("Ya se ha prestado una copia de este libro al usuario");
        } else {
            return commodateService.registerCommodate(book, user);
        }
    }

    public List<Book> getBooksForCatalogBook(CatalogBook catalogBook) throws SQLException {
        return bookRepository.findAllList().stream().filter(b -> b.getBookISBN().equals(catalogBook.getISBN())).toList();
    }

    public boolean userHasAlreadyBorrowedACopy(Book book, User user) throws SQLException {
        var commodates = commodateService.getCommodatesForUser(user);
        for (var commodate : commodates) {
            if (commodate.isActive()) {
                if (commodateService.getBookForCommodate(commodate).getBookISBN().equals(book.getBookISBN())) {
                    return true;
                }
            }
        }

        return false;
    }

    public class BookAlreadyBorrowedException extends RuntimeException {
        public BookAlreadyBorrowedException(String message) {
            super(message);
        }
    }
}
