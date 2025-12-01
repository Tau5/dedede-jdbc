package dedede.view;

import dedede.domain.Book;
import dedede.domain.User;
import dedede.repository.BookRepository;
import dedede.repository.IRepositorio;
import dedede.repository.IRepositorioExtend;
import dedede.repository.UserRepository;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;

final public class Model {
    public IRepositorioExtend<Book, Long> books;
    public IRepositorioExtend<User, Long> users;

    Model(Connection connection) throws IOException {
        this.books = new BookRepository(connection);
        this.users = new UserRepository(connection);
    }
}
