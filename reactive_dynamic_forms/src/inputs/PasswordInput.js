var React = require('react');

var PasswordInput = React.createClass({
    
    render: function () {
        return(
            <input id={this.props.id} type="password" value={this.props.value} onChange={this.handleChange}/>
        );
    },

    handleChange: function (e) {
        this.props.onChange(e.target.value)
    }
});

PasswordInput.cons = "PASSWORD";

module.exports = PasswordInput;