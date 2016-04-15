package fel.cvut.af.br.data;

import fel.cvut.af.br.model.Store;

import javax.enterprise.context.ApplicationScoped;

/**
 * Created by Zdeněk Brabec
 * brabezd1@fel.cvut.cz
 * 18.3.16
 */
@ApplicationScoped
public class StoreDAO extends GenericDAOImpl<Store> {

    public StoreDAO() {
        super(Store.class);
    }
}
