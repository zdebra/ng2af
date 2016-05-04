import React from 'react';
import LoginStore from '../stores/LoginStore';

export default (ComposedComponent) => {
    return class AuthenticatedComponent extends React.Component {

        constructor(props) {
            super(props);
            this.state = this._getLoginState();
        }

        _getLoginState() {
            return {
                userLoggedIn: LoginStore.isLoggedIn(),
                token: LoginStore.token
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
                <ComposedComponent
                    {...this.props}
                    token={this.state.token}
                    userLoggedIn={this.state.userLoggedIn} />
            );
        }
    }
};