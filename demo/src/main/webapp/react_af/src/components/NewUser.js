import React from 'react';
import AuthenticatedComponent from './AuthenticatedComponent';
import FormService from '../services/FormService';
import FormActions from '../actions/FormActions';
import FormStore from '../stores/FormStore';
import DynamicForm from 'reactiveAf';
import {withRouter} from 'react-router';
import UserService from '../services/UserService';
import {BASE} from '../constants/LoginConstants';

export default withRouter(AuthenticatedComponent(class NewUser extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            formStructure: FormStore.newUserForm
        }
    }

    componentDidMount() {
        this.changeListener = this._onChange.bind(this);
        FormStore.addChangeListener(this.changeListener);

        FormService
            .getFormStructure("newUserForm")
            .then((response) => {
                FormActions.newUserForm(response);
            })
            .catch((err) => {
                console.log(err);
            });
    }

    componentWillUnmount() {
        FormStore.removeChangeListener(this.changeListener);
    }

    _onChange() {
        this.setState({
            formStructure: FormStore.newUserForm
        });
    }

    getOptions(endpoint) {
        return FormService.getOptions(endpoint);
    }

    onSubmit(data) {
        var router = this.props.router;
        UserService
            .createUser(data)
            .then((response) => {
                router.replace(BASE);
            })
            .catch((err) => {
                console.log(err);
            });
    }

    render() {
        return(
            <div>
                <h2>Create User</h2>
                {this.state.formStructure !== null ? <DynamicForm structure={this.state.formStructure} onSubmit={this.onSubmit.bind(this)} getOptions={this.getOptions} /> : null}
            </div>
        );
    }

}));