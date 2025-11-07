package dedede.view.usuario;

import dedede.domain.User;
import dedede.view.MenuHelper;
import dedede.view.Model;
import dedede.view.View;
import dedede.view.ViewManager;

public class BorrowBooksView implements View {
    private User user;
    public BorrowBooksView(User user) {
        this.user = user;
    }

    @Override
    public void run(Model model, ViewManager viewManager) {
        var books = model.books;
        System.out.println("Lista de libros que no estan prestados");
        books.findAllList().forEach(book -> {
            if (!book.isBorrowed()) {
                System.out.println(book);
            }
        });
        var id = MenuHelper.getNumber("Ingrese el id del libro que quiere prestar");
        books.findAllList().forEach((book) -> {
            if (book.getID() == id) {
                if (!book.isBorrowed()) {
                    book.borrow(user);
                    model.books.save(book);
                    System.out.println("Libro prestado.");
                }
            }
        });

        viewManager.switchView(new UserHomeView(user));
    }
}
