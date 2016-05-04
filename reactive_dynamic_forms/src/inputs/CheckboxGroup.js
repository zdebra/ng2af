var React = require('react');

var Checkbox = React.createClass({

    render: function () {
        return(
            <label htmlFor={this.props.id}>
                <input type="checkbox" checked={this.props.checked} onChange={this.handleChange}/>
                {this.props.label}
            </label>
        );
    },

    handleChange: function () {
        this.props.toggle(this.props.id);
    }

});

var CheckboxGroup = React.createClass({
    render: function () {

        var options = [];
        for(var key in this.props.options) {
            options.push(
                <Checkbox
                    key={key}
                    id={key}
                    checked={this.props.value.indexOf(key) > -1}
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
        var value = this.props.value;
        var index = value.indexOf(name);
        if(index > -1) {
            value.splice(index,1);
        } else {
            value.push(name);
        }

        this.props.onChange(value);
    }

});


CheckboxGroup.cons = "CHECKBOX_GROUP";

module.exports = CheckboxGroup;