import AppDispatcher from '../dispatchers/AppDispatcher';
import {GET_ME} from '../constants/UserConstants';

export default {
    getMe: (user) => {

        AppDispatcher.dispatch({
            actionType: GET_ME,
            user: user
        });

    }
}