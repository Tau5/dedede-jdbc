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
        String title = MenuHelper.sc.nextLine();

        System.out.print("Autor: ");
        String author = MenuHelper.sc.nextLine();

        System.out.print("ISBN: ");
        String isbn = MenuHelper.sc.nextLine().strip();
        int stock = MenuHelper.getNumber("Cantidad: ");

        try {
            service.createCatalogBookWithStock(new CatalogBook(isbn, author, title), stock);
            System.out.println("Libro creado satisfactoriamente!");
        } catch (SQLException e) {
            System.out.println("Error: Error al insertar en la base de datos (" + e.getLocalizedMessage() + ")");
        } catch (CatalogBook.InvalidISBNException e) {
            System.out.println("Error: ISBN inválido");
        }


        viewManager.switchView(new ViewBibliotecario());
    }
}
