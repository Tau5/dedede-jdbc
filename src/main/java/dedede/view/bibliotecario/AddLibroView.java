package dedede.view.bibliotecario;

import dedede.domain.Book;
import dedede.view.MenuHelper;
import dedede.view.Model;
import dedede.view.View;
import dedede.view.ViewManager;

public class AddLibroView implements View {
    @Override
    public void run(Model model, ViewManager viewManager) {
        System.out.print("Título: ");
        MenuHelper.sc.nextLine();
        String title = MenuHelper.sc.nextLine();
        MenuHelper.sc.reset();
        System.out.print("Autor: ");
        String author = MenuHelper.sc.nextLine();

        Book book = new Book(title, author);

        model.books.save(book);

        System.out.println("Libro creado satisfactoriamente!");

        viewManager.switchView(new ViewBibliotecario());
    }
}
