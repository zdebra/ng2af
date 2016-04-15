package fel.cvut.af.br.service;

import fel.cvut.af.br.model.Author;
import fel.cvut.af.br.model.User;

import java.util.Collection;

/**
 * Created by zb on 20.2.16.
 */
public interface AuthorService {

    void addAuthor(Author author, User added) throws Exception;

    Author getAuthor(String name) throws Exception;

    Collection<Author> getAll() throws Exception;
}
