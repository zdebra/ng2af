var React = require('react');

var DateInput = React.createClass({

    render: function () {
        return(
            <input id={this.props.id} type="date" value={this.props.value} onChange={this.handleChange}/>
        );
    },

    handleChange: function (e) {
        this.props.onChange(e.target.value)
    }
});

DateInput.cons = "DATE";

module.exports = DateInput;