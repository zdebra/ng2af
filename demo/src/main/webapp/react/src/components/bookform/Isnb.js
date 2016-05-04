import React from 'react';
import ErrorMessage from './../ErrorMessage';


export default class Isnb extends React.Component {

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

        if(input.length > 8) {
            return false;
        }

        return true;
    }

    render() {
        return (
            <div className="pure-control-group">
                <label htmlFor="isnb">ISNB</label>
                <input
                    type="text"
                    id="isnb"
                    placeholder="ISNB number"
                    value={this.state.value}
                    onChange={this.onChange.bind(this)}
                />
                {(this.state.touched || this.props.submitted) && !this.state.valid ?
                    <ErrorMessage message="ISNB must be shorter than 9 chars"/> : null }
            </div>
        );
    }

}