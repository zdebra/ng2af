var React = require('react');

var SelectMany = React.createClass({
    render: function () {

        var options = [];
        for(var key in this.props.options) {
            options.push(<option key={key} value={key}>{this.props.options[key].label}</option>);
        }

        return(
            <select multiple={true} id={this.props.id} value={this.props.value} onChange={this.handleChange}>
                {options}
            </select>
        );
    },

    handleChange: function (e) {

        var options = e.target.options;
        var value = [];
        for (var i = 0, l = options.length; i < l; i++) {
            if (options[i].selected) {
                value.push(options[i].value);
            }
        }
        this.props.onChange(value);
    }

});

SelectMany.cons = "SELECT_MANY";

module.exports = SelectMany;