package dedede.view.usuario;

import dedede.domain.User;
import dedede.view.MenuHelper;
import dedede.view.Model;
import dedede.view.View;
import dedede.view.ViewManager;

public class ViewRegistrarse implements View {
    @Override
    public void run(Model model, ViewManager viewManager) {
        MenuHelper.sc.reset();
        System.out.print("nombre: ");
        String nombre = MenuHelper.sc.nextLine();
        System.out.print("apellidos: ");
        String apellidos = MenuHelper.sc.nextLine();

        User user = new User(
            nombre, apellidos
        );

        user = model.users.save(user);

        System.out.println(user.getID());

        viewManager.switchView(
            new UserHomeView(user)
        );
    }
}
