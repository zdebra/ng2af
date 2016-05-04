import React from 'react';
import ErrorMessage from './../ErrorMessage';


export default class Name extends React.Component {

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

        if(input.length < 1) {
            return false;
        }

        return true
    }

    render() {
        return (
            <div className="pure-control-group">
                <label htmlFor="name">Name</label>
                <input
                    type="text"
                    id="text"
                    placeholder="Name of place"
                    value={this.state.value}
                    onChange={this.onChange.bind(this)}
                />
                {(this.state.touched || this.props.submitted) && !this.state.valid ?
                    <ErrorMessage message="Name of place is required"/> : null }
            </div>
        );
    }

}