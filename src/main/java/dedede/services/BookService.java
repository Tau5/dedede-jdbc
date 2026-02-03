package dedede.services;

import dedede.domain.Book;
import dedede.domain.Commodate;

public class BookService {
    CommodateService commodateService;

    boolean isBookBorrowed(Book book) {
        return commodateService.getCommodatesForBook(book).stream().filter(Commodate::isActive).findFirst().isPresent();
    }
}
