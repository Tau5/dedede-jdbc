package dedede.services;

import dedede.domain.Book;
import dedede.domain.Commodate;
import dedede.domain.User;
import dedede.view.Model;

import java.sql.SQLException;
import java.util.List;

public class UserService {
    CommodateService commodateService;

    public UserService(Model model) {
        this.commodateService = new CommodateService(model);
    }

    public List<Commodate> getCommodates(User user) throws SQLException {
        return commodateService.getCommodatesForUser(user);
    }
}
