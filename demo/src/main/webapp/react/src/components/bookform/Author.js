import React from 'react';
import BookService from '../../services/BookService';


export default class Author extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            value: "",
            valid: true,
            touched: false,
            options: null
        }
    }

    componentDidMount() {
        BookService.authorOptions
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
        var value = e.target.value;
        this.setState({
            value: value,
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
                <label htmlFor="author">Author</label>
                <select id="author" onChange={this.onChange.bind(this)} value={this.state.value}>
                    {options}
                </select>
            </div>
        );
    }

}