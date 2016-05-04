import request from 'reqwest';
import when from 'when';
import {LOGIN_URL} from '../constants/LoginConstants';
import LoginActions from '../actions/LoginActions';

class AuthService {

    login(username, password) {
        return when(request({
            url: LOGIN_URL,
            method: 'POST',
            crossOrigin: true,
            type: 'json',
            contentType: 'application/json',
            data: JSON.stringify({username: username, password: password})
        }));
    }

    logout() {
        LoginActions.logoutUser();
    }
}

export default new AuthService()