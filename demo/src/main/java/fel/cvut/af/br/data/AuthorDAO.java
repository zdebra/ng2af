package fel.cvut.af.br.data;

import fel.cvut.af.br.model.Author;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

/**
 * Created by zb on 20.2.16.
 */
@ApplicationScoped
public class AuthorDAO extends GenericDAOImpl<Author> {

    public AuthorDAO() {
        super(Author.class);
    }

    public Author findByName(String name) {

        List<Author> authors = this.em.createNamedQuery(Author.class.getSimpleName()+".findByName", Author.class)
                .setParameter("name",name).getResultList();

        if(authors.size() == 0) {
            return null;
        } else {
            return authors.get(0);
        }

    }
}
