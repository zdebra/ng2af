import React from 'react';
import AuthenticatedComponent from './AuthenticatedComponent';
import FormService from '../services/FormService';
import FormActions from '../actions/FormActions';
import FormStore from '../stores/FormStore';
import PlaceService from '../services/PlaceService';
import DynamicForm from 'reactiveAf';
import {withRouter} from 'react-router';
import {BASE} from '../constants/LoginConstants';

export default withRouter(AuthenticatedComponent(class NewPlace extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            formStructure: FormStore.newPlaceForm
        }
    }

    componentDidMount() {
        this.changeListener = this._onChange.bind(this);
        FormStore.addChangeListener(this.changeListener);

        FormService
            .getFormStructure("newPlaceForm")
            .then((response) => {
                FormActions.newPlaceForm(response);
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
            formStructure: FormStore.newPlaceForm
        });
    }

    getOptions(endpoint) {
        return FormService.getOptions(endpoint);
    }

    onSubmit(data) {
        var router = this.props.router;
        PlaceService
            .createPlace(data)
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
                <h2>Create Place</h2>
                {this.state.formStructure !== null ? <DynamicForm structure={this.state.formStructure} onSubmit={this.onSubmit.bind(this)} getOptions={this.getOptions} /> : null}
            </div>
        );
    }

}));