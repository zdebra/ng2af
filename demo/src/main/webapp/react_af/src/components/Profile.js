import React from 'react';
import AuthenticatedComponent from './AuthenticatedComponent'
import UserService from '../services/UserService';
import TableColumn from './TableColumn';
import UserActions from '../actions/UserActions';
import UserStore from '../stores/UserStore';

export default AuthenticatedComponent(class Profile extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            me: UserStore.me
        }
    }

    componentDidMount() {
        this.changeListener = this._onChange.bind(this);
        UserStore.addChangeListener(this.changeListener);

        UserService.me
            .then((response) => {
                UserActions.getMe(response);
            })
            .catch((err) => {
                console.log(err);
            })

    }

    componentWillUnmount() {
        UserStore.removeChangeListener(this.changeListener);
    }

    _onChange() {
        this.setState({
            me: UserStore.me
        });
    }

    render() {

        return(
            <div>
                <h2>Your account info</h2>
                <table className="pure-table pure-table-horizontal">
                    <tbody>
                        <tr>
                            <th>Id</th>
                            <TableColumn content={this.state.me.id}/>
                        </tr>
                        <tr>
                            <th>Username</th>
                            <TableColumn content={this.state.me.username}/>
                        </tr>
                        <tr>
                            <th>Email</th>
                            <TableColumn content={this.state.me.email}/>
                        </tr>
                        <tr>
                            <th>Role</th>
                            <TableColumn content={this.state.me.role}/>
                        </tr>
                        <tr>
                            <th>Last time logged in</th>
                            <TableColumn content={this.state.me.lastLoggedIn}/>
                        </tr>
                        <tr>
                            <th>Created</th>
                            <TableColumn content={this.state.me.created}/>
                        </tr>
                    </tbody>
                </table>
            </div>
        );
    }

});