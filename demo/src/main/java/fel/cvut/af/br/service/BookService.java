package fel.cvut.af.br.service;

import fel.cvut.af.br.model.Book;
import fel.cvut.af.br.model.User;

import javax.ws.rs.NotFoundException;
import java.util.List;

/**
 * Created by zb on 12.1.16.
 */
public interface BookService {

    void addBook(Book book, User added) throws Exception;

    void removeBook(Book book, User removing) throws Exception;

    void editBook(Long id, Book book, User editing) throws Exception;

    Book getBook(String name) throws NotFoundException;

    Book getBook(Long id) throws NotFoundException;

    List<Book> getAll() throws Exception;

    List<Book> searchByName(String name) throws Exception;

    List<Book> searchByAuthor(String author) throws Exception;

}
