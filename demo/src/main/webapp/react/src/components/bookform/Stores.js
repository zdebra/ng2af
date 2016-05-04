import React from 'react';
import PlaceService from '../../services/PlaceService';
import update from 'react-addons-update';


export default class Stores extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            value: [],
            valid: true,
            touched: false,
            options: null
        }
    }

    componentDidMount() {
        PlaceService.storesOptions
            .with(this)
            .then((options) => {
                this.setState({
                    options: options
                });
            })
            .catch((err) => {
                console.log(err);
            })
    }

    onChange(e) {

        var options = e.target.options;
        var value = [];
        for (var i = 0, l = options.length; i < l; i++) {
            if (options[i].selected) {
                value.push(options[i].value);
            }
        }
        
        this.setState({
            value: update(this.state.value, {$set: value}),
            touched: true
        });
        this.props.onChange(value, this.state.valid);
    }

    render() {

        var options = [];
        if (this.state.options !== null) {
            for(var key in this.state.options) {
                options.push(<option key={this.state.options[key].name} value={this.state.options[key].value}>{this.state.options[key].label}</option>);
            }
        }


        return (
            <div className="pure-control-group">
                <label htmlFor="stores">Author</label>
                <select multiple id="stores" onChange={this.onChange.bind(this)} value={this.state.value}>
                    {options}
                </select>
            </div>
        );
    }

}