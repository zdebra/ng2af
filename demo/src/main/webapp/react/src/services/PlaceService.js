import request from 'reqwest';
import when from 'when';
import {PLACE_URL} from '../constants/PlaceConstants';
import {STORES_OPTION_URL} from '../constants/PlaceConstants';
import {LIBRARIES_OPTION_URL} from '../constants/PlaceConstants';

class PlaceService {

    get storesOptions() {
        return when(request({
            url: STORES_OPTION_URL,
            method: 'GET',
            crossOrigin: true,
            contentType: 'application/json',
            headers: {
                "Authorization": "Bearer " + localStorage.getItem('token')
            }
        }));
    }

    get librariesOptions() {
        return when(request({
            url: LIBRARIES_OPTION_URL,
            method: 'GET',
            crossOrigin: true,
            contentType: 'application/json',
            headers: {
                "Authorization": "Bearer " + localStorage.getItem('token')
            }
        }));
    }

    createPlace(place) {
        var data = {};

        data['id'] = -1;
        for(var key in place) {
            data[key] = place[key];
        }
        
        return when(request({
            url: PLACE_URL,
            method: 'POST',
            crossOrigin: true,
            contentType: 'application/json',
            type: 'json',
            data: JSON.stringify(data),
            headers: {
                "Authorization": "Bearer " + localStorage.getItem('token')
            }
        }));
    }
}

export default new PlaceService();