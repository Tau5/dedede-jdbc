package dedede.view.usuario;

import dedede.domain.User;
import dedede.view.MenuHelper;
import dedede.view.Model;
import dedede.view.View;
import dedede.view.ViewManager;

import java.sql.SQLException;

public class ReturnBookView implements View {
    private User user;
    public ReturnBookView(User user) {
        this.user = user;
    }

    @Override
    public void run(Model model, ViewManager viewManager) {
        var commodates = model.commodates;

        System.out.println("Libros que puede devolver:");
        try {
            commodates.findAllList().forEach(commodate -> {
                if (!(commodate.getUserID() == -1)) {
                    System.out.println(commodate);
                }
            });
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        var id = MenuHelper.getNumber("Ingresa el id del libro que quieres devolver:");
        try {
            commodates.findAllList().forEach(commodate -> {
                if (commodate.getBookID() == id) {
                    try {
                        model.commodates.deleteById(commodate.getBookID());
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        viewManager.switchView(new UserHomeView(user));
    }
}
