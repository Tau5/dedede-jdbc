package dedede.services;

import dedede.domain.Book;
import dedede.domain.CatalogBook;
import dedede.repository.BookRepository;
import dedede.repository.CatalogBookRepository;
import dedede.repository.CommodateRepository;
import dedede.view.Model;

import java.sql.SQLException;
import java.util.List;

public class CatalogService {
    BookService bookService;
    CatalogBookRepository catalogBookRepository;
    BookRepository bookRepository;

    public CatalogService(Model model) {
        this.bookService = new BookService(model);
        this.catalogBookRepository = model.catalog;
        this.bookRepository = model.books;
    }

    // Altamente ineficiente pero no me voy a poner a optimizarlo, ya lo haremos bien con JPA
    public List<Book> getAvailableBooksForCatalogBook(CatalogBook catalogBook) throws SQLException {
        return   bookService.getBooksForCatalogBook(catalogBook)
                .stream()
                .filter(bookService::isBookBorrowed)
                .toList();

    }

    public void createCatalogBookWithStock(CatalogBook catalogBook, int stock) throws SQLException {
       catalogBookRepository.save(catalogBook);
       for (int i = 0; i < stock; i++) {
           bookRepository.save(
                   new Book(-1L, catalogBook.getISBN())
           );
       }
    }

}
