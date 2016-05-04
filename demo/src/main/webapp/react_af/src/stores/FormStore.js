import {GET_NEW_BOOK_FORM} from '../constants/FormConstants';
import {GET_NEW_PLACE_FORM} from '../constants/FormConstants';
import {GET_NEW_USER_FORM} from '../constants/FormConstants';
import BaseStore from './BaseStore';

class FormStore extends BaseStore {

    constructor() {
        super();
        this.subscribe(() => this._registerToActions.bind(this))
        this._newBookForm = null;
        this._newPlaceForm = null;
        this._newUserForm = null
    }

    _registerToActions(action) {
        switch(action.actionType) {
            case GET_NEW_BOOK_FORM:
                this._newBookForm = action.structure;
                this.emitChange();
                break;
            case GET_NEW_PLACE_FORM:
                this._newPlaceForm = action.structure;
                this.emitChange();
                break;
            case GET_NEW_USER_FORM:
                this._newUserForm = action.structure;
                this.emitChange();
                break;
            default:
                break;
        };
    }

    get newBookForm() {
        return this._newBookForm;
    }

    get newPlaceForm() {
        return this._newPlaceForm;
    }

    get newUserForm() {
        return this._newUserForm;
    }
}

export default new FormStore();