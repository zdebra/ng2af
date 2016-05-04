import request from 'reqwest';
import when from 'when';
import {BOOK_URL} from '../constants/BookConstants';

class BookService {

    get allBooks() {
        return when(request({
            url: BOOK_URL,
            method: 'GET',
            crossOrigin: true,
            contentType: 'application/json',
            headers: {
                "Authorization": "Bearer " + localStorage.getItem('token')
            }
        }));
    }

    createBook(book) {
        book['id'] = -1;

        return when(request({
            url: BOOK_URL,
            method: 'POST',
            crossOrigin: true,
            contentType: 'application/json',
            type: 'json',
            data: JSON.stringify(book),
            headers: {
                "Authorization": "Bearer " + localStorage.getItem('token')
            }
        }));
    }
}

export default new BookService();