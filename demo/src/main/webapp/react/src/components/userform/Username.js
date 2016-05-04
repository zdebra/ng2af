import React from 'react';
import ErrorMessage from './../ErrorMessage';


export default class Username extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            value: "",
            valid: true,
            touched: false
        }
    }

    componentDidMount() {
        this.setState({
            valid: this.isValid(this.state.value)
        });
    }

    onChange(e) {
        var value = e.target.value;
        var valid = this.isValid(value);
        this.setState({
            value: value,
            valid: valid,
            touched: true
        });
        this.props.onChange(value, valid);
    }

    isValid(username) {
        if(username===null || username === undefined) {
            return false;
        }

        if(username.length < 5) {
            return false;
        }

        return true
    }

    render() {
        return (
            <div className="pure-control-group">
                <label htmlFor="username">Username</label>
                <input
                    type="text"
                    id="username"
                    placeholder="Type username"
                    value={this.state.value}
                    onChange={this.onChange.bind(this)}
                />
                {(this.state.touched || this.props.submitted) && !this.state.valid ?
                    <ErrorMessage message="Username is required and must be at least 5 characters long"/> : null }
            </div>
        );
    }

}