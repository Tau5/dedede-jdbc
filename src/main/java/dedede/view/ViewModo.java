package dedede.view;

import dedede.view.bibliotecario.ViewBibliotecario;
import dedede.view.usuario.UserView;

public class ViewModo implements View {
    @Override
    public void run(Model model, ViewManager viewManager) {
        var menu = new MenuHelper();

        menu.registerOption(1, "Usuario", UserView::new);
        menu.registerOption(2, "Bibliotecario", ViewBibliotecario::new);
        menu.registerOption(3, "Salir", () -> null);

        View nextView = menu.chooseAndExecute("opcion:");

        if (nextView != null) {
            viewManager.switchView(nextView);
        }

    }
}