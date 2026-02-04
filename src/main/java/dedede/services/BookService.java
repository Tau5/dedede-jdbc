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

    BookService(Model model) {
       this.commodateService = new CommodateService(model);
       this.bookRepository = model.books;
    }

    boolean isBookBorrowed(Book book) {
        return commodateService.getCommodatesForBook(book).stream().filter(Commodate::isActive).findFirst().isPresent();
    }

    Commodate borrowBook(Book book, User user) throws SQLException, BookAlreadyBorrowedException {
        if (!isBookBorrowed(book)) {
            throw new BookAlreadyBorrowedException();
        } else {
            return commodateService.registerCommodate(book, user);
        }
    }

    public List<Book> getBooksForCatalogBook(CatalogBook catalogBook) throws SQLException {
        return bookRepository.findAllList().stream().filter(b -> b.getBookISBN().equals(catalogBook.getISBN())).toList();
    }

    public class BookAlreadyBorrowedException extends RuntimeException { }
}
