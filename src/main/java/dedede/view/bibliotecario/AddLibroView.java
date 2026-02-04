package dedede.view.bibliotecario;

import dedede.domain.Book;
import dedede.domain.CatalogBook;
import dedede.services.BookService;
import dedede.services.CatalogService;
import dedede.view.MenuHelper;
import dedede.view.Model;
import dedede.view.View;
import dedede.view.ViewManager;

import java.sql.SQLException;

public class AddLibroView implements View {
    @Override
    public void run(Model model, ViewManager viewManager) {
        CatalogService service = new CatalogService(model);
        System.out.print("Título: ");
        MenuHelper.sc.nextLine();
        String title = MenuHelper.sc.nextLine();
        MenuHelper.sc.reset();
        System.out.print("Autor: ");
        String author = MenuHelper.sc.nextLine();
        System.out.println("ISBN: ");
        String isbn = MenuHelper.sc.nextLine();
        int stock = MenuHelper.getNumber("Cantidad: ");

        try {
            service.createCatalogBookWithStock(new CatalogBook(isbn, author, title), stock);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Libro creado satisfactoriamente!");

        viewManager.switchView(new ViewBibliotecario());
    }
}
