package fel.cvut.af.br.data;

import fel.cvut.af.br.model.Place;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

/**
 * Created by zb on 20.2.16.
 */
@ApplicationScoped
public class PlaceDAO extends GenericDAOImpl<Place> {

    public PlaceDAO() {
        super(Place.class);
    }

    public Place findByName(String name) {
        List<Place> places = this.em.createNamedQuery(Place.class.getSimpleName()+".findByName", Place.class)
                .setParameter("name",name).getResultList();

        if(places.size() == 0) {
            return null;
        } else {
            return places.get(0);
        }
    }
}
