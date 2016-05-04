import React from 'react';
import ErrorMessage from './../ErrorMessage';


export default class Role extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            value: "",
            valid: true,
            touched: false
        }
    }

    onChange(e) {
        var value = e.target.value;
        this.setState({
            value: value,
            touched: true
        });
        this.props.onChange(value, this.state.valid);
    }

    render() {
        return (
            <div className="pure-control-group">
                <label htmlFor="role">Password</label>
                <select id="role" onChange={this.onChange.bind(this)} value={this.state.value}>
                    <option value="visitor">Visitor</option>
                    <option value="editor">Editor</option>
                    <option value="manager">Manager</option>
                </select>
            </div>
        );
    }

}