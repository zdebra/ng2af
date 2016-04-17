package fel.cvut.af.br.service;

import fel.cvut.af.br.data.AuthorDAO;
import fel.cvut.af.br.data.UserDAO;
import fel.cvut.af.br.model.Author;
import fel.cvut.af.br.model.User;
import fel.cvut.af.br.util.AuthorAlreadyExistsException;
import fel.cvut.af.util.LoggingException;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by zb on 20.2.16.
 */
@Stateless
public class StandardAuthorService implements AuthorService {

    @Inject
    private UserDAO userDAO;

    @Inject
    private AuthorDAO authorDAO;

    @Inject
    private Logger logger;

    @Override
    public void addAuthor(Author author, User added) throws Exception {

        if(added == null) {
            throw new LoggingException("User not provided while adding new author", logger);
        }
        added = userDAO.find(added.getId());
        if(added == null) {
            throw new LoggingException("User wasn't found in database while adding new author", logger);
        }

        if(author.getName() == null) {
            throw new LoggingException("Author's name not provided!", logger);
        }
        Author existing = authorDAO.findByName(author.getName());
        if(existing!=null) {
            throw new AuthorAlreadyExistsException(existing, logger);
        }

        author.setAdded(added);
        authorDAO.create(author);

        Author created = authorDAO.find(author.getId());

        Collection<Author> authorsAdded = added.getAuthorsAdded();
        if(authorsAdded==null) {
            authorsAdded = new ArrayList<>();
        }
        authorsAdded.add(created);

        added.setAuthorsAdded(authorsAdded);
        userDAO.update(added);

        logger.log(Level.INFO, "Author " + created.getName() + " added by user " + added.getName());

    }

    @Override
    public Author getAuthor(String name) throws Exception {

        if(name==null) {
            throw new LoggingException("Name wasn't provided while getting author by name", logger);
        }
        Author author = authorDAO.findByName(name);

        if(author==null) {
            throw new LoggingException("Author "+name+" doesn't exist",logger);
        }

        logger.log(Level.INFO, "Retrieved author "+ name +" from database");

        return author;

    }

    @Override
    public Collection<Author> getAll() throws Exception {
        return this.authorDAO.findAll();
    }
}
