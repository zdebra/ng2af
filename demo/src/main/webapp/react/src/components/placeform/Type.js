import React from 'react';
import ErrorMessage from './../ErrorMessage';


export default class Type extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            value: "library",
            touched: false
        }
    }

    onChange(e) {
        var value = this.state.value == "library" ? "store" : "library";
        this.setState({
            value: value,
            touched: true
        });
        this.props.onChange(value, true);
    }

    render() {
        return (
            <div className="pure-control-group">
                <label>Type</label>
                <label class="pure-radio" for="library">
                    <input type="radio" id="library" checked={this.state.value == "library"} onChange={this.onChange.bind(this)}/>
                    Library
                </label>
                <label class="pure-radio" for="store">
                    <input type="radio" id="store" checked={this.state.value == "store"} onChange={this.onChange.bind(this)}/>
                    Store
                </label>
            </div>
        );
    }

}