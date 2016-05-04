import React from 'react';
import LoginStore from '../stores/LoginStore';
import UserStore from '../stores/UserStore';

export default (ComposedComponent) => {
    return class AuthenticatedComponent extends React.Component {

        constructor(props) {
            super(props);
            this.state = this._getLoginState();
        }

        _getLoginState() {
            return {
                userLoggedIn: LoginStore.isLoggedIn(),
                token: LoginStore.token,
                user: UserStore.me
            };
        }

        componentDidMount() {
            this.changeListener = this._onChange.bind(this);
            LoginStore.addChangeListener(this.changeListener);
            UserStore.addChangeListener(this.changeListener);
        }

        _onChange() {
            this.setState(this._getLoginState());
        }

        componentWillUnmount() {
            LoginStore.removeChangeListener(this.changeListener);
            UserStore.removeChangeListener(this.changeListener);
        }

        render() {
            return (
                <ComposedComponent
                    {...this.props}
                    token={this.state.token}
                    userLoggedIn={this.state.userLoggedIn}
                    user={this.state.user}
                />
            );
        }
    }
};