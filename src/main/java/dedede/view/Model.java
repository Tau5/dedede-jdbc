package dedede.view;

import dedede.domain.Book;
import dedede.domain.User;
import dedede.repository.BookRepository;
import dedede.repository.IRepositorio;
import dedede.repository.IRepositorioExtend;
import dedede.repository.UserRepository;

import java.io.File;
import java.io.IOException;

final public class Model {
    public IRepositorioExtend<Book, Long> books;
    public IRepositorioExtend<User, Long> users;

    Model(File booksFile, File usersFile) throws IOException {
        this.books = new BookRepository(booksFile);
        this.users = new UserRepository(usersFile);
    }
}
