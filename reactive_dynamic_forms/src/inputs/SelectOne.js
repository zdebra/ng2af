var React = require('react');

var SelectOne = React.createClass({

    render: function () {

        var options = [];
        for(var key in this.props.options) {
            options.push(<option key={key} value={key}>{this.props.options[key].label}</option>);
        }

        return(
            <select id={this.props.id} value={this.props.value} onChange={this.handleChange}>
                {options}
            </select>
        );
    },

    handleChange: function (e) {
        this.props.onChange(e.target.value)
    }
});

SelectOne.cons = "SELECT_ONE";

module.exports = SelectOne;