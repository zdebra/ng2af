import React from 'react';
import ErrorMessage from './../ErrorMessage';


export default class DateAdded extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            value: "",
            touched: false
        }
    }

    onChange(e) {
        var value = e.target.value;
        this.setState({
            value: value,
            touched: true
        });
        this.props.onChange(value, true);
    }

    render() {
        return (
            <div className="pure-control-group">
                <label htmlFor="dateAdded">Name</label>
                <input
                    type="date"
                    id="dateAdded"
                    placeholder="Enter a date when this book was added"
                    value={this.state.value}
                    onChange={this.onChange.bind(this)}
                />
            </div>
        );
    }

}