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

        try {
            model.catalog.deleteById(id);
        } catch (SQLException e) {
            System.out.println("Error: No existe un libro con ese ID (" + e.getLocalizedMessage() + ")");
        }

        viewManager.switchView(new ViewBibliotecario());
    }
}
