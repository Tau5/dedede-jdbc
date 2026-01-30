package dedede.view;

import dedede.domain.Book;
import dedede.domain.CatalogBook;
import dedede.domain.Commodate;
import dedede.domain.User;
import dedede.repository.*;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;

final public class Model {
    public IRepositorioExtend<Book, Long> books;
    public IRepositorioExtend<CatalogBook, String> catalog;
    public IRepositorioExtend<Commodate, Long> commodates;
    public IRepositorioExtend<User, Long> users;

    Model(Connection connection) {
        this.books = new BookRepository(connection);
        this.catalog = new CatalogBookRepository(connection);
        this.commodates = new CommodateRepository(connection);
        this.users = new UserRepository(connection);
    }
}
