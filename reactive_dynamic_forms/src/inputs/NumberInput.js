var React = require('react');

var NumberInput = React.createClass({
    
    render: function () {
        return(
            <input id={this.props.id} type="number" value={this.props.value} onChange={this.handleChange}/>
        );
    },

    handleChange: function (e) {
        this.props.onChange(e.target.value)
    }
});

NumberInput.cons = "NUMBER";

module.exports = NumberInput;