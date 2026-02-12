package dedede.view.usuario;

import dedede.domain.Book;
import dedede.domain.CatalogBook;
import dedede.domain.User;
import dedede.services.BookService;
import dedede.services.CatalogService;
import dedede.view.MenuHelper;
import dedede.view.Model;
import dedede.view.View;
import dedede.view.ViewManager;

import java.sql.SQLException;
import java.util.List;

public class BorrowBooksView implements View {
    private User user;
    public BorrowBooksView(User user) {
        this.user = user;
    }

    @Override
    public void run(Model model, ViewManager viewManager) {
        var catalog = model.catalog;
        var catalogService = new CatalogService(model);
        var bookService = new BookService(model);
        List<CatalogBook> listBook;
        List<Book> listBookAvaible;
        System.out.println("Lista de libros que no estan prestados");

        try {
            listBook = catalog.findAllList();
            listBook.forEach(b -> System.out.println(b));
            System.out.print("Elige el ISBN del libro que prestar:");
            var id = MenuHelper.sc.nextLine();
            catalog.findByIdOptional(id).ifPresent(cb -> {
                try {
                    var libros = catalogService.getAvailableBooksForCatalogBook(cb);

                    if (libros.size() < 1) {
                        System.out.println("No hay libros disponibles para ese libro");
                    } else {
                        var libro = libros.getFirst();
                        bookService.borrowBook(libro, user);
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

            });
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        viewManager.switchView(new UserHomeView(user));
    }
}
