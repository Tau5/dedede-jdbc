package dedede.view.usuario;

import dedede.domain.User;
import dedede.view.MenuHelper;
import dedede.view.Model;
import dedede.view.View;
import dedede.view.ViewManager;

public class ReturnBookView implements View {
    private User user;
    public ReturnBookView(User user) {
        this.user = user;
    }

    @Override
    public void run(Model model, ViewManager viewManager) {
        var books = model.books;

        System.out.println("Libros que puede devolver:");
        books.findAllList().forEach(book -> {
            if (book.isBorrowed()) {
                System.out.println(book);
            }
        });
        var id = MenuHelper.getNumber("Ingresa el id del libro que quieres devolver:");
        books.findAllList().forEach(book -> {
            if (book.getID() == id) {
                book.returnBook();
                model.books.save(book);
            }
        });

        viewManager.switchView(new UserHomeView(user));
    }
}
