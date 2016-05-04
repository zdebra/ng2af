import request from 'reqwest';
import when from 'when';
import {FORM_URL} from '../constants/FormConstants';
import {OPTIONS_URL} from '../constants/FormConstants';

class FormService {

    getFormStructure(name) {
        return when(request({
            url: FORM_URL + name,
            method: 'GET',
            crossOrigin: true,
            contentType: 'application/json',
            headers: {
                "Authorization": "Bearer " + localStorage.getItem('token')
            }
        }));
    }

    getOptions(endpoint) {
        return when(request({
            url: OPTIONS_URL + endpoint,
            method: 'GET',
            crossOrigin: true,
            contentType: 'application/json',
            headers: {
                "Authorization": "Bearer " + localStorage.getItem('token')
            }
        }));
    }
    
}

export default new FormService();