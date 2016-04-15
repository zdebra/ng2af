package fel.cvut.af.br.service;

import fel.cvut.af.br.data.LibraryDAO;
import fel.cvut.af.br.data.StoreDAO;
import fel.cvut.af.br.model.Library;
import fel.cvut.af.br.model.Place;
import fel.cvut.af.br.model.Store;
import fel.cvut.af.br.data.PlaceDAO;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

/**
 * Created by Zdeněk Brabec
 * brabezd1@fel.cvut.cz
 * 18.3.16
 */

@Stateless
public class StandardPlaceService implements PlaceService {

    @Inject
    private PlaceDAO placeDAO;

    @Inject
    private LibraryDAO libraryDAO;

    @Inject
    private StoreDAO storeDAO;



    @Override
    public Place addPlace(Place place) throws Exception {

        if(place==null) {
            throw new Exception("Place to add wasn't provided");
        }

        if(place.getName() == null | place.getName().isEmpty()) {
            throw new Exception("Place's name must be given");
        }

        if(place.getAddress() == null) {
            throw new Exception("Place's address must be given");
        }

        placeDAO.create(place);
        return placeDAO.find(place.getId());


    }

    @Override
    public List<? extends Place> getAll(Class<? extends Place> placeType) {

        if(placeType.equals(Library.class)) {
            return libraryDAO.findAll();
        }

        if(placeType.equals(Store.class)) {
            return storeDAO.findAll();
        }

        return placeDAO.findAll();

    }

    @Override
    public Place getPlace(String name) {
        return placeDAO.findByName(name);
    }
}
