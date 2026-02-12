package dedede.view.usuario;

import dedede.domain.CatalogBook;
import dedede.domain.User;
import dedede.services.CommodateService;
import dedede.view.Model;
import dedede.view.View;
import dedede.view.ViewManager;

import java.sql.SQLException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class BorrowedBooksView implements View {
    private User user;
    public BorrowedBooksView(User user) {
        this.user = user;
    }

    @Override
    public void run(Model model, ViewManager viewManager) {
        var commodates = model.commodates;
        var commodateService = new CommodateService(model);
        try {
            commodateService.getCommodatesForUser(user).forEach((commodate) -> {
                if (commodate.isActive()) {
                    try {
                        CatalogBook cb = commodateService.getCatalogBookForCommodate(commodate);
                        System.out.println(cb + ", Caduca en: " + Instant.now().until(commodate.getPeriodEnd(), ChronoUnit.DAYS) + " días");
                    } catch (SQLException e) {
                        System.out.println("Error al obtener información de un libro (" + e.getLocalizedMessage() + ")");
                    }
                }
            });
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        viewManager.switchView(new UserHomeView(user));
    }
}
