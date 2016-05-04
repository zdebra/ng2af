import React from 'react';
import AuthenticatedComponent from './../AuthenticatedComponent';
import {withRouter} from 'react-router';
import Username from './Username';
import Password from './Password';
import Email from './Email';
import Role from './Role';
import UserService from '../../services/UserService';
import update from 'react-addons-update';
import ErrorMessage from '../ErrorMessage';
import {BASE} from '../../constants/LoginConstants';

export default withRouter(AuthenticatedComponent(class NewUser extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            username: "",
            password: "",
            email: "",
            role: "visitor",
            valid: {
                username: true,
                password: true,
                email: true,
                role: true
            },
            submitted: false
        }
        this.isValid = this.isValid.bind(this);

    }


    usernameChange(value, valid) {
        var vObj = this.state.valid;
        vObj["username"] = valid;
        this.setState({
            username: value,
            valid: update(this.state.valid, {$set: vObj})
        });
    }

    passwordChange(value, valid) {
        var vObj = this.state.valid;
        vObj["password"] = valid;
        this.setState({
            password: value,
            valid: update(this.state.valid, {$set: vObj})
        });
    }

    emailChange(value, valid) {
        var vObj = this.state.valid;
        vObj["email"] = valid;
        this.setState({
            email: value,
            valid: update(this.state.valid, {$set: vObj})
        });
    }

    roleChange(value, valid) {
        var vObj = this.state.valid;
        vObj["role"] = valid;
        this.setState({
            role: value,
            valid: update(this.state.valid, {$set: vObj})
        });
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
        console.log(this.state);
        this.setState({
           submitted: true
        });

        if(this.isValid()) {
            var user = {
                username: this.state.username,
                password: this.state.password,
                email: this.state.email,
                role: this.state.role
            };
            var router = this.props.router;
            UserService
                .createUser(user)
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


    render() {

        if(this.props.user === null) {
            return null;
        }
        var content;

        if(this.props.user.role === "ADMIN") {
            content = (
                <fieldset>
                    <Username onChange={this.usernameChange.bind(this)} submitted={this.state.submitted}/>
                    <Password onChange={this.passwordChange.bind(this)} submitted={this.state.submitted}/>
                    <Email onChange={this.emailChange.bind(this)} submitted={this.state.submitted}/>
                    <Role onChange={this.roleChange.bind(this)} submitted={this.state.submitted}/>
                </fieldset>
            );
        } else {
            content = null;
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