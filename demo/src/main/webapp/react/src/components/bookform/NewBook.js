import React from 'react';
import AuthenticatedComponent from './../AuthenticatedComponent';
import BookService from '../../services/BookService';
import {withRouter} from 'react-router';
import Name from './Name';
import ErrorMessage from '../ErrorMessage';
import update from 'react-addons-update';
import Description from './Description';
import Author from './Author';
import Isnb from './Isnb';
import Status from './Status';
import Stores from './Stores';
import Price from './Price';
import Libraries from './Libraries';
import Visibility from './Visibility';
import DateAdded from './DateAdded';
import {BASE} from '../../constants/LoginConstants';

export default withRouter(AuthenticatedComponent(class NewBook extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            name: "",
            description: "",
            author: "",
            isnb: "",
            status: "active",
            stores: [],
            price: "",
            libraries: [],
            visibility: [],
            dateAdded: "",
            valid: {
                name: false,
                description: false,
                author: false,
                isnb: true,
                status: true,
                stores: true,
                price: false,
                libraries: true,
                visibility: true,
                dateAdded: true
            },
            submitted: false
        };

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
            var book = {
                name: this.state.name,
                description: this.state.description,
                author: this.state.author,
                isnb: this.state.isnb,
                status: this.state.status,
                stores: this.state.stores,
                price: this.state.price,
                libraries: this.state.libraries,
                visibility: this.state.visibility,
                dateAdded: this.state.dateAdded
            };
            var router = this.props.router;
            BookService
                .createBook(book)
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

    descriptionChange(value, valid) {
        var vObj = this.state.valid;
        vObj["description"] = valid;
        this.setState({
            description: value,
            valid: update(this.state.valid, {$set: vObj})
        });
    }

    authorChange(value, valid) {
        var vObj = this.state.valid;
        vObj["author"] = valid;
        this.setState({
            author: value,
            valid: update(this.state.valid, {$set: vObj})
        });
    }

    isnbChange(value, valid) {
        var vObj = this.state.valid;
        vObj["isnb"] = valid;
        this.setState({
            isnb: value,
            valid: update(this.state.valid, {$set: vObj})
        });
    }

    statusChange(value, valid) {
        var vObj = this.state.valid;
        vObj["status"] = valid;
        this.setState({
            status: value,
            valid: update(this.state.valid, {$set: vObj})
        });
    }

    storesChange(value, valid) {
        var vObj = this.state.valid;
        vObj["stores"] = valid;
        this.setState({
            stores: value,
            valid: update(this.state.valid, {$set: vObj})
        });
    }

    priceChange(value, valid) {
        var vObj = this.state.valid;
        vObj["price"] = valid;
        this.setState({
            price: value,
            valid: update(this.state.valid, {$set: vObj})
        });
    }

    librariesChange(value, valid) {
        var vObj = this.state.valid;
        vObj["libraries"] = valid;
        this.setState({
            libraries: value,
            valid: update(this.state.valid, {$set: vObj})
        });
    }

    visibilityChange(value, valid) {
        var vObj = this.state.valid;
        vObj["visibility"] = valid;
        this.setState({
            visibility: value,
            valid: update(this.state.valid, {$set: vObj})
        });
    }

    dateAddedChange(value, valid) {
        var vObj = this.state.valid;
        vObj["dateAdded"] = valid;
        this.setState({
            dateAdded: value,
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
                        <Name onChange={this.nameChange.bind(this)} submitted={this.state.submitted}/>
                        <Description onChange={this.descriptionChange.bind(this)} submitted={this.state.submitted}/>
                        <Author onChange={this.authorChange.bind(this)} submitted={this.state.submitted}/>
                        <Isnb onChange={this.isnbChange.bind(this)} submitted={this.state.submitted}/>
                        <Status onChange={this.statusChange.bind(this)} submitted={this.state.submitted}/>
                    </fieldset>
                );
                break;

            case "MANAGER":
                content = (
                    <fieldset>
                        <Name onChange={this.nameChange.bind(this)} submitted={this.state.submitted}/>
                        <Description onChange={this.descriptionChange.bind(this)} submitted={this.state.submitted}/>
                        <Author onChange={this.authorChange.bind(this)} submitted={this.state.submitted}/>
                        <Isnb onChange={this.isnbChange.bind(this)} submitted={this.state.submitted}/>
                        <Status onChange={this.statusChange.bind(this)} submitted={this.state.submitted}/>
                        <Stores onChange={this.storesChange.bind(this)} submitted={this.state.submitted}/>
                        <Price onChange={this.priceChange.bind(this)} submitted={this.state.submitted}/>
                        <Libraries onChange={this.librariesChange.bind(this)} submitted={this.state.submitted}/>
                        <Visibility onChange={this.visibilityChange.bind(this)} submitted={this.state.submitted}/>
                    </fieldset>
                );
                break;

            default:
                content = (
                    <fieldset>
                        <Name onChange={this.nameChange.bind(this)} submitted={this.state.submitted}/>
                        <Description onChange={this.descriptionChange.bind(this)} submitted={this.state.submitted}/>
                        <Author onChange={this.authorChange.bind(this)} submitted={this.state.submitted}/>
                        <Isnb onChange={this.isnbChange.bind(this)} submitted={this.state.submitted}/>
                        <Status onChange={this.statusChange.bind(this)} submitted={this.state.submitted}/>
                        <Stores onChange={this.storesChange.bind(this)} submitted={this.state.submitted}/>
                        <Price onChange={this.priceChange.bind(this)} submitted={this.state.submitted}/>
                        <Libraries onChange={this.librariesChange.bind(this)} submitted={this.state.submitted}/>
                        <Visibility onChange={this.visibilityChange.bind(this)} submitted={this.state.submitted}/>
                        <DateAdded onChange={this.dateAddedChange.bind(this)} submitted={this.state.submitted}/>
                    </fieldset>
                );
        }

        return(
            <div>
                <h2>Create book</h2>
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
