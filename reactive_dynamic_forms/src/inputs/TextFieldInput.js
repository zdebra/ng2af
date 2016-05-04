var React = require('react');

var TextFieldInput = React.createClass({

    render: function () {
        return(
            <textarea id={this.props.id} type="text" value={this.props.value} onChange={this.handleChange}/>
        );
    },

    handleChange: function (e) {
        this.props.onChange(e.target.value)
    }
});

TextFieldInput.cons = "TEXTFIELD";

module.exports = TextFieldInput;