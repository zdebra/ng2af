package fel.cvut.af.br.data;

import fel.cvut.af.br.model.Library;

import javax.enterprise.context.ApplicationScoped;

/**
 * Created by Zdeněk Brabec
 * brabezd1@fel.cvut.cz
 * 18.3.16
 */
@ApplicationScoped
public class LibraryDAO extends GenericDAOImpl<Library> {

    public LibraryDAO() {
        super(Library.class);
    }
}
