var React = require('react');

var TextInput = React.createClass({
    
    render: function () {
        return(
            <input id={this.props.id} type="text" value={this.props.value} onChange={this.handleChange}/>
        );
    },

    handleChange: function (e) {
        this.props.onChange(e.target.value)
    }
});

TextInput.cons = "TEXT";

module.exports = TextInput;