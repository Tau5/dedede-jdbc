package dedede.view.usuario;

import dedede.view.MenuHelper;
import dedede.view.Model;
import dedede.view.View;
import dedede.view.ViewManager;

public class UserView implements View {
    @Override
    public void run(Model model, ViewManager viewManager) {
        MenuHelper menu = new MenuHelper();

        menu.registerOption(1, "Iniciar sesión", ViewIniciarSesion::new);
        menu.registerOption(2, "Registrarse", ViewRegistrarse::new);

        View view = menu.chooseAndExecute("opción:");

        viewManager.switchView(view);
    }
}
