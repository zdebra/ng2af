import ReactDOM from 'react-dom';
import React from 'react';
import {Router, Route, IndexRoute} from 'react-router';
import AuthenticatedApp from './components/AuthenticatedApp'
import Login from './components/Login';
import Home from './components/Home';
import {browserHistory} from 'react-router';
import LoginActions from './actions/LoginActions';
import LoginStore from './stores/LoginStore';
import Profile from './components/Profile';
import NewBook from './components/bookform/NewBook';
import NewPlace from './components/placeform/NewPlace';
import NewUser from './components/userform/NewUser';
import {BASE} from './constants/LoginConstants';


let token = localStorage.getItem('token');
if (token) {
    LoginActions.loginUser(token);
}

function requireAuth(nextState, replace) {
    if(!LoginStore.isLoggedIn()) {
        replace({
            pathname: BASE + "/login",
            state: { nextPathname: nextState.location.pathname }
        })
    }
}

ReactDOM.render(
    <Router history={browserHistory}>
        <Route path={BASE} component={AuthenticatedApp}>
            <IndexRoute component={Home} onEnter={requireAuth}/>
            <Route path="login" component={Login}/>
            <Route path="home" component={Home} onEnter={requireAuth}/>
            <Route path="profile" component={Profile} onEnter={requireAuth}/>
            <Route path="book" component={NewBook} onEnter={requireAuth}/>
            <Route path="place" component={NewPlace} onEnter={requireAuth}/>
            <Route path="user" component={NewUser} onEnter={requireAuth}/>
        </Route>
    </Router>
    , document.getElementById('app'));
