import React from 'react';
import ErrorMessage from './../ErrorMessage';


export default class Password extends React.Component {

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

    isValid(input) {
        if(input===null || input === undefined) {
            return false;
        }

        if(input.length < 8) {
            return false;
        }

        return true
    }

    render() {
        return (
            <div className="pure-control-group">
                <label htmlFor="password">Password</label>
                <input
                    type="password"
                    id="password"
                    placeholder="Type password"
                    value={this.state.value}
                    onChange={this.onChange.bind(this)}
                />
                {(this.state.touched || this.props.submitted) && !this.state.valid ?
                    <ErrorMessage message="Password is required and must be at least 8 characters long"/> : null }
            </div>
        );
    }

}