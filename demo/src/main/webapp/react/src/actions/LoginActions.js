import AppDispatcher from '../dispatchers/AppDispatcher';
import {LOGIN_USER} from '../constants/LoginConstants';
import {LOGOUT_USER} from '../constants/LoginConstants';
import UserService from '../services/UserService';
import UserActions from '../actions/UserActions';

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

        UserService
            .me
            .with(this)
            .then((response)=>{
                UserActions.getMe(response);
            })
            .catch((err) => {
                console.log(err);
            });

    },
    logoutUser: () => {
        localStorage.removeItem('token');
        AppDispatcher.dispatch({
            actionType: LOGOUT_USER
        });
    }
}