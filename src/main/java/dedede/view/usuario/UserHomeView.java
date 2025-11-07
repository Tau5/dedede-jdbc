package dedede.view.usuario;

import dedede.domain.User;
import dedede.view.*;

public class UserHomeView implements View {
    private User user;
    public UserHomeView(User user) {
        this.user = user;
    }

    @Override
    public void run(Model model, ViewManager viewManager) {

        MenuHelper menu = new MenuHelper();

        menu.registerOption(1, "Ver libros prestados", () -> new BorrowedBooksView(user));
        menu.registerOption(2, "Pedir libro prestado", () -> new BorrowBooksView(user));
        menu.registerOption(3, "Devolver libro", () -> new ReturnBookView(user));
        menu.registerOption(4, "Eliminar cuenta", () -> new DeleteUserView(user));
        menu.registerOption(5, "Salir", ViewModo::new);

        View nextView = menu.chooseAndExecute("opcion:");
        viewManager.switchView(nextView);
    }
}
