import AppDispatcher from '../dispatchers/AppDispatcher';
import {GET_NEW_BOOK_FORM} from '../constants/FormConstants';
import {GET_NEW_USER_FORM} from '../constants/FormConstants';
import {GET_NEW_PLACE_FORM} from '../constants/FormConstants';

export default {
    newBookForm: (structure) => {

        AppDispatcher.dispatch({
            actionType: GET_NEW_BOOK_FORM,
            structure: structure
        });

    },
    newPlaceForm: (structure) => {
        AppDispatcher.dispatch({
            actionType: GET_NEW_PLACE_FORM,
            structure: structure
        });

    },
    
    newUserForm: (structure) => {
        AppDispatcher.dispatch({
            actionType: GET_NEW_USER_FORM,
            structure: structure
        });

    }
}