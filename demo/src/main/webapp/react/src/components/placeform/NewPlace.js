import React from 'react';
import AuthenticatedComponent from './../AuthenticatedComponent';
import PlaceService from '../../services/PlaceService';
import {withRouter} from 'react-router';
import Name from './Name';
import City from './City';
import State from './State';
import Street from './Street';
import Zip from './Zip';
import Type from './Type';
import update from 'react-addons-update';
import ErrorMessage from '../ErrorMessage';
import {BASE} from '../../constants/LoginConstants';

export default withRouter(AuthenticatedComponent(class NewPlace extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            name: "",
            type: "library",
            street: "",
            city: "",
            state: "",
            zip: "",
            valid: {
                name: false,
                type: true,
                street: false,
                city: false,
                state: false,
                zip: false
            },
            submitted: false
        }
        this.isValid = this.isValid.bind(this);

    }

    isValid() {
        for(var key in this.state.valid) {
            if(!this.state.valid[key]) {
                return false;
            }
        }

        return true;
    }

    onFormSubmit(e) {
        e.preventDefault();
        this.setState({
            submitted: true
        });

        if(this.isValid()) {
            var place = {
                name: this.state.name,
                placeType: this.state.type,
                street: this.state.street,
                city: this.state.city,
                state: this.state.state,
                zip: this.state.zip
            };
            var router = this.props.router;
            PlaceService
                .createPlace(place)
                .then((response) => {
                    router.replace(BASE);
                })
                .catch((err) => {
                    console.log(err);
                });
        } else {
            console.log("Form is invalid");
        }

    }

    nameChange(value, valid) {
        var vObj = this.state.valid;
        vObj["name"] = valid;
        this.setState({
            name: value,
            valid: update(this.state.valid, {$set: vObj})
        });
    }

    streetChange(value, valid) {
        var vObj = this.state.valid;
        vObj["street"] = valid;
        this.setState({
            street: value,
            valid: update(this.state.valid, {$set: vObj})
        });
    }

    cityChange(value, valid) {
        var vObj = this.state.valid;
        vObj["city"] = valid;
        this.setState({
            city: value,
            valid: update(this.state.valid, {$set: vObj})
        });
    }

    stateChange(value, valid) {
        var vObj = this.state.valid;
        vObj["state"] = valid;
        this.setState({
            state: value,
            valid: update(this.state.valid, {$set: vObj})
        });
    }

    zipChange(value, valid) {
        var vObj = this.state.valid;
        vObj["zip"] = valid;
        this.setState({
            zip: value,
            valid: update(this.state.valid, {$set: vObj})
        });
    }

    typeChange(value, valid) {
        var vObj = this.state.valid;
        vObj["type"] = valid;
        this.setState({
            type: value,
            valid: update(this.state.valid, {$set: vObj})
        });
    }

    render() {

        if(this.props.user === null) {
            return null;
        }
        var content;

        switch(this.props.user.role) {
            case "VISITOR":
                content = (
                    <fieldset>
                    </fieldset>
                );
                break;

            case "EDITOR":
                content = (
                    <fieldset>
                    </fieldset>
                );
                break;

            case "MANAGER":
                content = (
                    <fieldset>
                        <Name onChange={this.nameChange.bind(this)} submitted={this.state.submitted}/>
                        <Type onChange={this.typeChange.bind(this)} submitted={this.state.submitted}/>
                        <Street onChange={this.streetChange.bind(this)} submitted={this.state.submitted}/>
                        <City onChange={this.cityChange.bind(this)} submitted={this.state.submitted}/>
                        <State onChange={this.stateChange.bind(this)} submitted={this.state.submitted}/>
                        <Zip onChange={this.zipChange.bind(this)} submitted={this.state.submitted}/>
                    </fieldset>
                );
                break;

            default:
                content = (
                    <fieldset>
                        <Name onChange={this.nameChange.bind(this)} submitted={this.state.submitted}/>
                        <Type onChange={this.typeChange.bind(this)} submitted={this.state.submitted}/>
                        <Street onChange={this.streetChange.bind(this)} submitted={this.state.submitted}/>
                        <City onChange={this.cityChange.bind(this)} submitted={this.state.submitted}/>
                        <State onChange={this.stateChange.bind(this)} submitted={this.state.submitted}/>
                        <Zip onChange={this.zipChange.bind(this)} submitted={this.state.submitted}/>
                    </fieldset>
                );
        }


        return(
            <div>
                <h2>Create new user</h2>
                <form className="pure-form pure-form-aligned">
                    {content}
                    <div className="pure-controls">
                        <button className="pure-button pure-button-primary" type="submit" onClick={this.onFormSubmit.bind(this)}>Submit</button>
                        {this.state.submitted && !this.isValid() ? <ErrorMessage message="Form is invalid" /> : null}
                    </div>
                </form>
            </div>
        );

    }

}));