package fel.cvut.af.br.service;

import fel.cvut.af.br.model.Place;

import java.util.List;

/**
 * Created by Zdeněk Brabec
 * brabezd1@fel.cvut.cz
 * 18.3.16
 */
public interface PlaceService {

    Place addPlace(Place place) throws Exception;

    List<? extends Place> getAll(Class<? extends Place> placeType);

    Place getPlace(String name);

}
