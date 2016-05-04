import {GET_BOOKS} from '../constants/BookConstants';
import BaseStore from './BaseStore';

class BookStore extends BaseStore {

    constructor() {
        super();
        this.subscribe(() => this._registerToActions.bind(this))
        this._books = [];
    }

    _registerToActions(action) {
        switch(action.actionType) {
            case GET_BOOKS:
                this._books = action.books;
                this.emitChange();
                break;
            default:
                break;
        };
    }

    get books() {
        return this._books;
    }
}

export default new BookStore();