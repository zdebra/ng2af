import AppDispatcher from '../dispatchers/AppDispatcher';
import {GET_BOOKS} from '../constants/BookConstants';

export default {
    getAllBooks: (books) => {

        AppDispatcher.dispatch({
            actionType: GET_BOOKS,
            books: books
        });

    }
}