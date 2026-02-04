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
    public BookRepository books;
    public CatalogBookRepository catalog;
    public CommodateRepository commodates;
    public UserRepository users;

    Model(Connection connection) {
        this.books = new BookRepository(connection);
        this.catalog = new CatalogBookRepository(connection);
        this.commodates = new CommodateRepository(connection);
        this.users = new UserRepository(connection);
    }
}
