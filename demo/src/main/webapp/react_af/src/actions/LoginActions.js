import AppDispatcher from '../dispatchers/AppDispatcher';
import {LOGIN_USER} from '../constants/LoginConstants';
import {LOGOUT_USER} from '../constants/LoginConstants';

export default {
    loginUser: (token) => {
        var savedToken = localStorage.getItem('token');

        AppDispatcher.dispatch({
            actionType: LOGIN_USER,
            token: token
        });

        if (savedToken !== token) {
            localStorage.setItem('token', token);
        }

    },
    logoutUser: () => {
        localStorage.removeItem('token');
        AppDispatcher.dispatch({
            actionType: LOGOUT_USER
        });
    }
}