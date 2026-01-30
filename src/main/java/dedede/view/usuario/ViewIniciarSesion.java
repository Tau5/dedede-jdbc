package dedede.view.usuario;

import dedede.domain.User;
import dedede.view.MenuHelper;
import dedede.view.Model;
import dedede.view.View;
import dedede.view.ViewManager;

import java.sql.SQLException;

public class ViewIniciarSesion implements View {
    @Override
    public void run(Model model, ViewManager viewManager) {
        try {
            model.users.findAllList().forEach(user -> {
                System.out.println(
                        user.getID() + ": " + user.getName() + " " + user.getSurname()
                );
            });
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        int chosen = -1;
        while (true) {
            try {
                if (!model.users.existsById((long) chosen)) break;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            chosen = MenuHelper.getNumber("id:");
        }

        User usuario = null;
        try {
            usuario = model.users.findById((long) chosen);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        System.out.println(usuario.getName());

        viewManager.switchView(
            new UserHomeView(usuario)
        );
    }
}
