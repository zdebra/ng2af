import React from 'react';
import Auth from '../services/AuthService';
import {withRouter} from 'react-router';
import LoginActions from '../actions/LoginActions';
import {BASE} from '../constants/LoginConstants';

export default withRouter(class Login extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            user: '',
            password: ''
        };

    }

    login(e) {
        e.preventDefault();

        Auth.login(this.state.user, this.state.password)
            .with(this)
            .then(function(response) {
                var token = response.token;
                LoginActions.loginUser(token);
                this.props.router.replace(BASE);
            })
            .catch(function(err) {
                console.log("Error logging in", err);
            });
    }

    handleUsernameChange(e) {
        this.setState({user: e.target.value});
    }


    handlePasswordChange(e) {
        this.setState({password: e.target.value});
    }

    render() {
        return (
            <div className="pure-g">
                <div className="pure-u-1">
                    <h1>Please, log in.</h1>
                    <form className="pure-form pure-form-aligned">

                        <fieldset>

                            <div className="pure-control-group">
                                <label htmlFor="username">Username</label>
                                <input
                                    id="username"
                                    type="text"
                                    placeholder="username"
                                    value={this.state.user}
                                    onChange={this.handleUsernameChange.bind(this)}
                                />
                            </div>

                            <div className="pure-control-group">
                                <label htmlFor="password">Password</label>
                                <input
                                    id="password"
                                    type="password"
                                    placeholder="password"
                                    value={this.state.password}
                                    onChange={this.handlePasswordChange.bind(this)}
                                />
                            </div>

                            <div className="pure-controls">
                                <button type="submit" className="pure-button pure-button-primary" onClick={this.login.bind(this)}>Log In</button>
                            </div>

                        </fieldset>

                    </form>
                </div>
            </div>
        );
    }
});
