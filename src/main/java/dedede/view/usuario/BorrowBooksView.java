package dedede.view.usuario;

import dedede.domain.Commodate;
import dedede.domain.User;
import dedede.view.MenuHelper;
import dedede.view.Model;
import dedede.view.View;
import dedede.view.ViewManager;

import java.sql.SQLException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;

public class BorrowBooksView implements View {
    private User user;
    public BorrowBooksView(User user) {
        this.user = user;
    }

    @Override
    public void run(Model model, ViewManager viewManager) {
        var commodates = model.commodates;
        System.out.println("Lista de libros que no estan prestados");
        try {
            commodates.findAllList().forEach(commodate -> {
                if (commodate.getUserID() == -1) {
                    System.out.println(commodate);
                }
            });
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        var id = MenuHelper.getNumber("Ingrese el id del libro que quiere prestar");
        try {
            commodates.findAllList().forEach((commodate) -> {
                if (commodate.getBookID() == id) {
                    if (commodate.getUserID() == -1) {
                        var newCommodate = new Commodate(
                                -1,
                                Instant.now(),
                                Instant.now()
                                        .plus(15, ChronoUnit.DAYS),
                                user.getID(),
                                commodate.getBookID()
                        );
                        try {
                            model.commodates.save(newCommodate);
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                        System.out.println("Libro prestado.");
                    }
                }
            });
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        viewManager.switchView(new UserHomeView(user));
    }
}
