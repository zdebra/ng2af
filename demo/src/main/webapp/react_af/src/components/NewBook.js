import React from 'react';
import AuthenticatedComponent from './AuthenticatedComponent';
import DynamicForm from 'reactiveAf';
import FormStore from '../stores/FormStore';
import FormService from '../services/FormService';
import FormActions from '../actions/FormActions';
import BookService from '../services/BookService';
import {withRouter} from 'react-router';
import {BASE} from '../constants/LoginConstants';

export default withRouter(AuthenticatedComponent(class NewBook extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            formStructure: FormStore.newBookForm
        }
    }

    componentDidMount() {
        this.changeListener = this._onChange.bind(this);
        FormStore.addChangeListener(this.changeListener);

        FormService.getFormStructure("newBookForm")
            .then((response) => {
                FormActions.newBookForm(response);
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
           formStructure: FormStore.newBookForm
        });
    }

    getOptions(endpoint) {
        return FormService.getOptions(endpoint);
    }

    onSubmit(data) {
        var router = this.props.router;
        BookService
            .createBook(data)
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
                <h2>Create book</h2>
                {this.state.formStructure !== null ? <DynamicForm structure={this.state.formStructure} onSubmit={this.onSubmit.bind(this)} getOptions={this.getOptions} /> : null}
            </div>
        );
    }

}));
