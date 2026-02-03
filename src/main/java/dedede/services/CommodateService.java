package dedede.services;

import dedede.domain.Book;
import dedede.domain.Commodate;
import dedede.repository.CommodateRepository;

import java.sql.SQLException;
import java.util.List;

public class CommodateService {
    CommodateRepository commodateRepository;

    List<Commodate> getCommodatesForBook(Book book) {
        try {
            return commodateRepository
                    .findAllList()
                    .stream()
                    .filter(c -> c.getBookID() == book.getID())
                    .toList();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
