package dedede.view.usuario;

import dedede.domain.User;
import dedede.view.*;

import java.sql.SQLException;

public class DeleteUserView implements View {

    private User user;

    public DeleteUserView(User user) {
        this.user = user;
    }
    @Override
    public void run(Model model, ViewManager viewManager) {
        System.out.print("¿Estás segure (y/N)? ");
        MenuHelper.sc.nextLine();
        String response = MenuHelper.sc.nextLine();
        if (response.equals("y")) {
            try {
                model.users.deleteById(user.getID());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            viewManager.switchView(new ViewModo());
        } else {
            viewManager.switchView(new UserHomeView(user));
        }
    }

}
