package dedede.view.bibliotecario;

import dedede.view.*;

public class ViewBibliotecario implements View {
    @Override
    public void run(Model model, ViewManager viewManager) {
        System.out.println("Modo bibliotecario");

        MenuHelper menu = new MenuHelper();

        menu.registerOption(1, "Añadir libro", AddLibroView::new);
        menu.registerOption(2, "Eliminar libro", DeleteLibroView::new);
        menu.registerOption(3, "Listar libros", ListBooksView::new);
        menu.registerOption(4, "Salir", ViewModo::new);

        View view = menu.chooseAndExecute("opcion:");

        viewManager.switchView(view);
    }
}
