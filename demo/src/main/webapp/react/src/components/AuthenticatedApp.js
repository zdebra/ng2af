'use strict';

import React from 'react';
import LoginStore from '../stores/LoginStore'
import {Link} from 'react-router';
import AuthService from '../services/AuthService';
import {withRouter} from 'react-router';
import {BASE} from '../constants/LoginConstants';

export default withRouter(class AuthenticatedApp extends React.Component {
    constructor(props) {
        super(props);
        this.state = this._getLoginState();
    }

    _getLoginState() {
        return {
            userLoggedIn: LoginStore.isLoggedIn()
        };
    }

    componentDidMount() {
        this.changeListener = this._onChange.bind(this);
        LoginStore.addChangeListener(this.changeListener);
    }

    _onChange() {
        this.setState(this._getLoginState());
    }

    componentWillUnmount() {
        LoginStore.removeChangeListener(this.changeListener);
    }

    render() {
        return (
            <div className="container">
                <div className="pure-g">
                    <div className="pure-u-1">
                        <div className="pure-menu pure-menu-horizontal">
                            <Link className="pure-menu-heading pure-menu-link" to="/">BOOK REPOSITORY</Link>
                            <ul className="pure-menu-list">
                                <li className="pure-menu-item">
                                    <Link to={BASE + "/profile"} className="pure-menu-link">My Profile</Link>
                                </li>
                                <li className="pure-menu-item">
                                    <Link to={BASE + "/book"} className="pure-menu-link">Add book</Link>
                                </li>
                                <li className="pure-menu-item">
                                    <Link to={BASE + "/place"} className="pure-menu-link">Add place</Link>
                                </li>
                                <li className="pure-menu-item">
                                    <Link to={BASE + "/user"} className="pure-menu-link">Add user</Link>
                                </li>
                                <li className="pure-menu-item">
                                    <a className="pure-menu-link" onClick={this.logout.bind(this)}>Logout</a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
                {this.props.children}
            </div>
        );
    }

    logout(e) {
        e.preventDefault();
        AuthService.logout();
        this.props.router.replace(BASE + "/login");
    }

});