import React from 'react';
import PlaceService from '../../services/PlaceService';
import update from 'react-addons-update';

class Checkbox extends React.Component {

    render() {
        return(
            <label htmlFor={this.props.id}>
                <input type="checkbox" checked={this.props.checked} onChange={this.handleChange.bind(this)}/>
                {this.props.label}
            </label>
        );
    }

    handleChange() {
        this.props.toggle(this.props.id);
    }

};


export default class Visibility extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            value: [],
            valid: true,
            touched: false
        }
    }

    toggle(name) {
        var value = this.state.value;
        var index = value.indexOf(name);
        if(index > -1) {
            value.splice(index,1);
        } else {
            value.push(name);
        }
        this.setState({
            value: update(this.state.value, {$set: value})
        });
        this.props.onChange(value, true);
    }

    render() {

        return (
            <div className="pure-control-group">
                <label>Visibility</label>
                <Checkbox
                    id="visitor"
                    checked={this.state.value.indexOf("visitor") > -1}
                    label="Visitor"
                    toggle={this.toggle.bind(this)}
                />
                <Checkbox
                    id="editor"
                    checked={this.state.value.indexOf("editor") > -1}
                    label="Editor"
                    toggle={this.toggle.bind(this)}
                />
                <Checkbox
                    id="manager"
                    checked={this.state.value.indexOf("manager") > -1}
                    label="Manager"
                    toggle={this.toggle.bind(this)}
                />
            </div>
        );
    }

}