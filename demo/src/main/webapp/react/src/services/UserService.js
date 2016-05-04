import request from 'reqwest';
import when from 'when';
import {GET_ME_URL} from '../constants/UserConstants';
import {USER_URL} from '../constants/UserConstants';

class UserService {

    get me() {
        return when(request({
            url: GET_ME_URL,
            method: 'GET',
            crossOrigin: true,
            contentType: 'application/json',
            headers: {
                "Authorization": "Bearer " + localStorage.getItem('token')
            }
        }));
    }

    createUser(user) {
        var data = {};

        data['id'] = -1;
        for(var key in user) {
            data[key] = user[key];
        }
        
        return when(request({
            url: USER_URL,
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

export default new UserService();