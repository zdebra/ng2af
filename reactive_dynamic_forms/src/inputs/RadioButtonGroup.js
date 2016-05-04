var React = require('react');

var RadioButton = React.createClass({

    render: function () {
        return(
            <label htmlFor={this.props.id}>
                <input type="radio" checked={this.props.checked} onChange={this.handleChange}/>
                {this.props.label}
            </label>
        );
    },

    handleChange: function () {
        this.props.toggle(this.props.id);
    }

});

var RadioButtonGroup = React.createClass({
    render: function () {
        
        var options = [];
        for(var key in this.props.options) {
            options.push(
                <RadioButton
                    key={key}
                    id={key}
                    checked={this.props.value === key}
                    label={this.props.options[key].label}
                    toggle={this.toggle}
                />
            );
        }

        return(
            <div id={this.props.id}>
                {options}
            </div>
        );
    },

    toggle: function (name) {
        this.props.onChange(this.props.options[name].name);
    }

});


RadioButtonGroup.cons = "RADIOBUTTON_GROUP";

module.exports = RadioButtonGroup;