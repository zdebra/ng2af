package fel.cvut.af.br.data;

import fel.cvut.af.br.model.Book;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

/**
 * Created by zb on 12.1.16.
 */
@ApplicationScoped
public class BookDAO extends GenericDAOImpl<Book> {
    public BookDAO() {
        super(Book.class);
    }

    public Book findByName(String name) {
        List<Book> books = this.em.createNamedQuery(Book.class.getSimpleName()+".findByName", Book.class)
                .setParameter("name",name).getResultList();

        if(books.size() == 0) {
            return null;
        } else {
            return books.get(0);
        }

    }

    public List<Book> searchByName(String name) {
        List<Book> books = this.em.createNamedQuery(Book.class.getSimpleName()+".searchByName", Book.class)
                .setParameter("name",name).getResultList();

        return books;

    }


    public List<Book> searchByAuthor(String author) {
        List<Book> books = this.em.createNamedQuery(Book.class.getSimpleName()+".searchByAuthor", Book.class)
                .setParameter("author",author).getResultList();

        return books;

    }
}
