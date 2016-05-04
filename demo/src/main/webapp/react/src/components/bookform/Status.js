import React from 'react';
import ErrorMessage from './../ErrorMessage';


export default class Status extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            value: "active",
            touched: false
        }
    }

    onChange(e) {
        var value = this.state.value == "active" ? "inactive" : "active";
        this.setState({
            value: value,
            touched: true
        });

        this.props.onChange(value == "active" ? true : false, true);
    }

    render() {
        return (
            <div className="pure-control-group">
                <label>Type</label>
                <label class="pure-radio" for="active">
                    <input type="radio" id="active" checked={this.state.value == "active"} onChange={this.onChange.bind(this)}/>
                    Active
                </label>
                <label class="pure-radio" for="inactive">
                    <input type="radio" id="inactive" checked={this.state.value == "inactive"} onChange={this.onChange.bind(this)}/>
                    Inactive
                </label>
            </div>
        );
    }

}