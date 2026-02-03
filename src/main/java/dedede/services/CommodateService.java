package dedede.services;

import dedede.domain.Book;
import dedede.domain.Commodate;
import dedede.domain.User;
import dedede.repository.CommodateRepository;

import java.sql.SQLException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAmount;
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

    Commodate registerCommodate(Book book, User user) throws SQLException {
        return commodateRepository.save(
                new Commodate(-1, Instant.now(), Instant.now().plus(15, ChronoUnit.DAYS), user.getID(), book.getID())
        );
    }

}
