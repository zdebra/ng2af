package fel.cvut.af.br.service;

import fel.cvut.af.br.data.AuthorDAO;
import fel.cvut.af.br.data.BookDAO;
import fel.cvut.af.br.data.PlaceDAO;
import fel.cvut.af.br.data.UserDAO;
import fel.cvut.af.br.model.*;
import fel.cvut.af.util.LoggingException;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.inject.Inject;
import javax.naming.NoPermissionException;
import javax.ws.rs.NotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Created by zb on 12.1.16.
 */
@Stateless
public class StandardBookService implements BookService {

    @Inject
    private UserDAO userDAO;

    @Inject
    private BookDAO bookDAO;

    @Inject
    private AuthorDAO authorDAO;

    @Inject
    private PlaceDAO placeDAO;

    @Inject
    private Logger logger;

    @Override
    public void addBook(Book book, User added) throws LoggingException {

        try {
            if (book == null) {
                throw new Exception("Book isn't provided");
            }
            Book existing = bookDAO.findByName(book.getName());
            if (existing != null) {
                throw new Exception("The book " + existing.getName() + " already exists!");
            }

            added = userDAO.find(added.getId());
            if (added == null) {
                throw new Exception("User not found");
            }
        } catch (Exception e) {
            throw new LoggingException(e.getMessage(),logger);
        }

        book.setAdded(added);
        added.addBook(book);

        bookDAO.create(book);

        book = bookDAO.find(book.getId());

        // author
        Author authorCopy = book.getAuthor();
        if(authorCopy != null && authorCopy.getId() != null) {
            Author author = authorDAO.find(authorCopy.getId());
            if(author==null) {
                throw new LoggingException("Author of id "+authorCopy.getId()+" not found ",logger);
            }
            author.addBookWritten(book);
            authorDAO.update(author);
            book.setAuthor(author);
        }

        // libraries
        Collection<Place> librariesCopy = book.getLibraries();
        if(librariesCopy != null) {

            ArrayList<Place> libraries = new ArrayList<>(librariesCopy.size());

            for (Place place : librariesCopy) {
                Place p = placeDAO.find(place.getId());
                if(p==null) {
                    throw new LoggingException("Library of id "+p.getId()+" not found ",logger);
                }
                p.addBook(book);
                libraries.add(p);
                placeDAO.update(p);

            }
            book.setLibraries(libraries);

        }

        // stores
        Collection<Place> storesCopy = book.getStores();
        if(storesCopy != null) {

            ArrayList<Place> stores = new ArrayList<>(storesCopy.size());

            for (Place place : storesCopy) {
                Place p = placeDAO.find(place.getId());
                if(p==null) {
                    throw new LoggingException("Store of id "+p.getId()+" not found ",logger);
                }
                p.addBook(book);
                stores.add(p);
                placeDAO.update(p);
            }
            book.setLibraries(stores);

        }

        // visibility
        Collection<Role> visibilityCopy = book.getVisibility();
        Collection<Role> visibilityRoles;
        if(visibilityCopy == null) {
            visibilityRoles = new ArrayList<>();
            visibilityRoles.add(Role.ADMIN);
        } else {
            visibilityRoles = new ArrayList<>(visibilityCopy.size());
            visibilityRoles.addAll(visibilityCopy.stream().collect(Collectors.toList()));
        }
        book.setVisibility(visibilityRoles);

        bookDAO.update(book);

        book = bookDAO.find(book.getId());

        Collection<Book> userBooksAdded = added.getBooksAdded();

        if(userBooksAdded == null) {
            userBooksAdded = new ArrayList<>();
        }
        userBooksAdded.add(book);
        added.setBooksAdded(userBooksAdded);


        userDAO.update(added);

    }

    @Override
    public void removeBook(Book book, User removing) throws Exception {

        if(book==null ) {
            throw new Exception("Book isn't provided");
        }

        book = bookDAO.find(book.getId());
        if(book==null ) {
            throw new Exception("No book found");
        }

        if(removing==null) {
            throw new Exception("User who is removing the book isn't provided");
        }

        removing = userDAO.find(removing.getId());
        if(removing==null) {
            throw new Exception("User who is removing the book wasn't found");
        }

        Role userRole = removing.getRole();
        if(userRole != Role.ADMIN || userRole != Role.MANAGER) {
            throw new NoPermissionException("User " +removing.getName() + " has no permission to delete a book");
        }

        bookDAO.delete(book.getId());

    }

    @Override
    @TransactionAttribute
    public void editBook(Long id, Book book, User editing) throws Exception {

        if(book==null ) {
            throw new Exception("Book isn't provided");
        }

        if(id==null) {
            throw new Exception("No id provided");
        }

        Book oldBook = bookDAO.find(id);
        if(oldBook == null ) {
            throw new Exception("No book found");
        }

        if(editing==null) {
            throw new Exception("User who is removing the book isn't provided");
        }

        editing = userDAO.find(editing.getId());
        if(editing==null) {
            throw new Exception("User who is removing the book wasn't found");
        }

        Book existing = bookDAO.findByName(book.getName());
        if(existing!=null && existing.getName().equals(book.getName()) && existing.getId() != oldBook.getId()) {
            throw new Exception("Book with this name already exists");
        }

        oldBook.setName(book.getName());
        oldBook.setDescription(book.getDescription());
        oldBook.setAuthor(book.getAuthor());
        oldBook.setISNB(book.getISNB());

        bookDAO.update(oldBook);

    }

    @Override
    public Book getBook(String name) throws NotFoundException {

        if(name==null) {
            throw new NotFoundException("Name of book isn't provided.");
        }

        Book book = bookDAO.findByName(name);

        if(book==null) {
            throw new NotFoundException("Book of name " + name + " doesn't exists");
        }

        return book;

    }

    @Override
    public Book getBook(Long id) throws NotFoundException {

        if(id==null) {
            throw new NotFoundException("Id of book isn't provided.");
        }

        Book book = bookDAO.find(id);

        if(book==null) {
            throw new NotFoundException("Book of id " + id + " doesn't exists");
        }

        return book;
    }

    @Override
    public List<Book> getAll() throws Exception {

        List<Book> books = bookDAO.findAll();
        if(books==null) {
            throw new Exception("No books found");
        }
        return books;

    }

    @Override
    public List<Book> searchByName(String name) throws Exception {

        if(name==null) {
            throw new Exception("Name of book isn't provided");
        }

        List<Book> result = bookDAO.searchByName(name);

        if(result==null) {
            throw new Exception("No books found");
        }

        return result;

    }

    @Override
    public List<Book> searchByAuthor(String author) throws Exception {

        if(author==null) {
            throw new Exception("Author of book isn't provided");
        }

        List<Book> result = bookDAO.searchByAuthor(author);

        if(result==null) {
            throw new Exception("No books found");
        }

        return result;

    }
}
