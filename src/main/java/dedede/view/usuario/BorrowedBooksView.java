package dedede.view.usuario;

import dedede.domain.User;
import dedede.view.Model;
import dedede.view.View;
import dedede.view.ViewManager;

import java.sql.SQLException;
import java.time.Instant;

public class BorrowedBooksView implements View {
    private User user;
    public BorrowedBooksView(User user) {
        this.user = user;
    }

    @Override
    public void run(Model model, ViewManager viewManager) {
        var commodates = model.commodates;
        try {
            commodates.findAllList().forEach((commodate) -> {
                if ((commodate.getUserID() == user.getID()) && (commodate.getPeriodEnd().isAfter(Instant.now()))) {
                    System.out.println(commodate);
                }
            });
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        viewManager.switchView(new UserHomeView(user));
    }
}
