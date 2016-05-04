import React from 'react';
import ErrorMessage from './../ErrorMessage';


export default class Email extends React.Component {

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
        var pattern = "[-a-z0-9~!$%^&*_=+}{\'?]+(\.[-a-z0-9~!$%^&*_=+}{\'?]+)*@([a-z0-9_][-a-z0-9_]*(\.[-a-z0-9_]+)*\.(aero|arpa|biz|com|coop|edu|gov|info|int|mil|museum|name|net|org|pro|travel|mobi|[a-z][a-z])|([0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}))(:[0-9]{1,5})?$";
        var regExp = new RegExp(pattern);
        return regExp.test(input);
    }

    render() {
        return (
            <div className="pure-control-group">
                <label htmlFor="email">Email</label>
                <input
                    type="text"
                    id="email"
                    placeholder="Type email address"
                    value={this.state.value}
                    onChange={this.onChange.bind(this)}
                />
                {(this.state.touched || this.props.submitted) && !this.state.valid ?
                    <ErrorMessage message="Enter valid email address"/> : null }
            </div>
        );
    }

}