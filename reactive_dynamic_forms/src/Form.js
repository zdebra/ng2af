var React = require("react");
var InputWrapper = require('./InputWrapper');
var ErrorMessage = require('./ErrorMessage');
var update = require("react-addons-update");

/**
 * @params structure form structure object
 * @params onSubmit callback function executed on valid form submit, data are passed as a function parameter
 * @params getOptions callback function executed for obtaining options from endpoint, must return those data
 * @params setValid
 */
var Form = React.createClass({


    getInitialState: function () {

        var data = {};
        var valid = {};
        this.props.structure.inputs.forEach(function (input) {
            data[input.name] = input.defaultValue;
            valid[input.name] = true;
        });

        return({
            touched: false,
            data: data,
            valid: valid
        });
    },

    render: function () {

        var getOptions = this.props.getOptions;
        var touched = this.state.touched;
        var inputChange = this.inputChange;
        var setValid = this.setValid;

        var inputs = this.props.structure.inputs.map(function (input) {
            return(
                <InputWrapper
                    key={input.name}
                    structure={input}
                    getOptions={getOptions}
                    inputChange={inputChange}
                    touched={touched}
                    setValid={setValid}
                />
            );
        });
        
        return(
            <form novalidate name={this.props.structure.name} className={this.props.structure.formClass} onSubmit={this.handleFormSubmit}>
                {inputs}
                <div className={this.props.structure.buttonSurroundingClass}>
                    <button type="submit" className={this.props.structure.submitButtonClass}>Submit</button>
                </div>
                { this.state.touched && !this.isValid() ? <ErrorMessage className={this.props.structure.formErrorClass} message="Form is invalid"/> : null}
            </form>
        );
    },

    handleFormSubmit: function (e) {
        e.preventDefault();
        this.setState({
            touched: true
        });

        if(this.isValid()) {
            var data = this.formatData(this.state.data);
            this.props.onSubmit(data);
        } else {
            console.log("Form is invalid");
        }
    },

    inputChange: function (name, value, valid) {
        var newData = {};
        newData[name] = value;
        var newValid = {};
        newValid[name] = valid;

        this.setState({
            touched: true,
            data: update(this.state.data, {$merge: newData}),
            valid: update(this.state.valid, {$merge: newValid})
        });

    },

    setValid: function (name, valid) {
        var newValid = this.state.valid;
        newValid[name] = valid;

        this.setState({
            valid: update(this.state.valid, {$set: newValid})
        });

    },

    isValid: function () {
        for(var key in this.state.valid) {
            if(this.state.valid[key] === false) {
                return false;
            }
        }
        return true;
    },

    formatData: function (values) {
        var data = {};
        var structure = this.props.structure;

        for (var i = 0; i < structure.inputs.length; i++) {
            var name = structure.inputs[i].name;
            if (structure.inputs[i].options || structure.inputs[i].endpoint) {

                switch(structure.inputs[i].responseFormat) {

                    case "VALUE":
                        data[name] = values[name].value;
                        break;
                    case "NAME":
                        data[name] = values[name].name;
                        break;
                    case "OPTION":
                        data[name] = values[name];
                        break;
                    case "VALUES":
                        data[name] = [];
                        for(var j = 0; j < values[name].length; j++) {
                            data[name].push(values[name][j].value);
                        }
                        break;
                    case "NAMES":
                        data[name] = [];
                        for(var j = 0; j < values[name].length; j++) {
                            data[name].push(values[name][j].name);
                        }
                        break;
                    case "OPTIONS":
                        data[name] = [];
                        for(var j = 0; j < values[name].length; j++) {
                            data[name].push(values[name][j]);
                        }
                        break;
                    default:
                        //no op

                }

            } else {
                data[structure.inputs[i].name] = values[name];
            }
        }

        return data;
    }

});

module.exports = Form;