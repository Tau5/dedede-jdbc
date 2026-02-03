package dedede.services;

import dedede.domain.Book;
import dedede.domain.Commodate;
import dedede.domain.User;

import java.sql.SQLException;
import java.util.List;

public class UserService {
    CommodateService commodateService;

    List<Commodate> getCommodates(User user) throws SQLException {
        return commodateService.getCommodatesForUser(user);
    }
}
