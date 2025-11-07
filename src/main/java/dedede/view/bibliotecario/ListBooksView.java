package dedede.view.bibliotecario;

import dedede.view.Model;
import dedede.view.View;
import dedede.view.ViewManager;

public class ListBooksView implements View {

    @Override
    public void run(Model model, ViewManager viewManager) {

        var books = model.books;
        books.findAllList().forEach(System.out::println);
        viewManager.switchView(new ViewBibliotecario());
    }
}
