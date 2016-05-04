import {GET_ME} from '../constants/UserConstants';
import BaseStore from './BaseStore';

class UserStore extends BaseStore {

    constructor() {
        super();
        this.subscribe(() => this._registerToActions.bind(this))
        this._me = null;
    }

    _registerToActions(action) {
        switch(action.actionType) {
            case GET_ME:
                this._me = action.user;
                this.emitChange();
                break;
            default:
                break;
        };
    }

    get me() {
        return this._me;
    }
}

export default new UserStore();