package dedede.view.usuario;

import dedede.domain.Commodate;
import dedede.domain.User;
import dedede.repository.CommodateRepository;
import dedede.services.CommodateService;
import dedede.view.MenuHelper;
import dedede.view.Model;
import dedede.view.View;
import dedede.view.ViewManager;

import java.sql.SQLException;
import java.util.List;

public class ReturnBookView implements View {
    private User user;
    public ReturnBookView(User user) {
        this.user = user;
    }

    @Override
    public void run(Model model, ViewManager viewManager) {
        var commodateM = model.commodates;
        var commodateService = new CommodateService(model);
        List<Commodate> commodatesList;

        System.out.println("Libros que puede devolver:");
        try {
            commodatesList = commodateService.getCommodatesForUser(user);
            for (Commodate commodate : commodatesList) {
                System.out.println(commodate.getId() + " " + commodateService.getCatalogBookForCommodate(commodate));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        var id = MenuHelper.getNumber("Ingresa el id del libro que quieres devolver:");

        commodatesList.forEach(commodate -> {
            if (commodate.getId() == id) {
                try {
                    commodateM.deleteById((long) id);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        viewManager.switchView(new UserHomeView(user));
    }
}
