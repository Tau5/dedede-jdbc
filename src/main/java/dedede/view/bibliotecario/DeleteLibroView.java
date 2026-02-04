package dedede.view.bibliotecario;

import dedede.domain.CatalogBook;
import dedede.view.MenuHelper;
import dedede.view.Model;
import dedede.view.View;
import dedede.view.ViewManager;

import java.sql.SQLException;
import java.util.Optional;

public class DeleteLibroView implements View {
    @Override
    public void run(Model model, ViewManager viewManager) {
        try {
            model.catalog.findAll().forEach(book -> {
                System.out.println(book);
            });
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        System.out.print("ISBN a eliminar: ");
        String id = MenuHelper.sc.nextLine();

        Optional<CatalogBook> maybeBook = null;
        try {
            maybeBook = model.catalog.findByIdOptional(id);

            maybeBook.ifPresentOrElse(
                    book -> {
                        try {
                            model.catalog.deleteById(book.getISBN());
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    },
                    () -> {
                        System.out.println("Error: No existe un libro con ese ID");
                    }
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        viewManager.switchView(new ViewBibliotecario());
    }
}
