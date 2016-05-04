var React = require("react");
var update = require("react-addons-update");
var ErrorMessage = require('./ErrorMessage');

var RegExpValidator = require('./Validator').RegExp;
var NumberMinValidator = require('./Validator').NumberMin;
var NumberMaxValidator = require('./Validator').NumberMax;
var DateSinceValidator = require('./Validator').DateSince;
var DateUntilValidator = require('./Validator').DateUntil;
var EmailValidator = require('./Validator').Email;
var RequiredValidator = require('./Validator').Required;
var StrLenMinValidator = require('./Validator').StrLenMin;
var StrLenMaxValidator = require('./Validator').StrLenMax;

var TextInput = require('./inputs/TextInput');
var NumberInput = require('./inputs/NumberInput');
var TextFieldInput = require('./inputs/TextFieldInput');
var DateInput = require('./inputs/DateInput');
var SelectOne = require('./inputs/SelectOne');
var SelectMany = require('./inputs/SelectMany');
var CheckboxGroup = require('./inputs/CheckboxGroup');
var RadioButtonGroup = require('./inputs/RadioButtonGroup');
var PasswordInput = require('./inputs/PasswordInput');

/**
 * @paramms structure input structure
 * @params getOptions callback function executed for obtaining options from endpoint, must return those data
 * @params inputChange callback function called on input change, args: name,value,valid
 * @params touched input
 */
var InputWrapper = React.createClass({

    getInitialState: function () {
        var val = this.props.structure.defaultValue;
        if(val === null || val === undefined) {
            val = "";
        }

        var type = this.props.structure.inputType;
        if(type === SelectMany.cons || type === CheckboxGroup.cons) {
            var arr = [];
            for(var key in val) {
                arr.push(key);
            }
            val = arr;
        }

        var touched = this.props.touched !== 'undefined' && this.props.touched !== null ? this.props.touched : false;
        return({
            value: val,
            error: this.isValid(val),
            touched: touched,
            options: null
        });
    },

    componentDidMount: function () {
        this.getOptions();
    },

    componentWillReceiveProps: function (nextProps) {
        this.setState({touched: nextProps.touched});
    },
    
    componentWillMount: function () {
        this.props.setValid(this.props.structure.name, this.state.error.length == 0);
    },

    onChange: function (value) {
        var errors = this.isValid(value);

        this.setState({
            value: value,
            touched: true,
            error: update(this.state.error, {$set: errors})
        });

        var optValue;
        if(this.state.options !== null) {
            if (typeof value === 'string') {
                optValue = this.state.options[value];
            } else {
                optValue = [];
                for(var i = 0; i < value.length; i++) {
                    optValue.push(this.state.options[value[i]]);
                }
            }

        } else {
            optValue = value;
        }
        
        this.props.inputChange(this.props.structure.name, optValue, errors.length == 0);
    },

    isValid: function (value) {

        var error = [];

        var validators = this.props.structure.validators;
        for(var key in validators) {
            if(validators.hasOwnProperty(key)) {

                switch (key) {
                    case RegExpValidator.cons:
                        if(!RegExpValidator.validate(validators[key].value, value)) {
                            error.push(validators[key].message);
                        };
                        break;

                    case NumberMinValidator.cons:
                        if(!NumberMinValidator.validate(validators[key].value,value)){
                            error.push(validators[key].message);
                        };
                        break;

                    case NumberMaxValidator.cons:
                        if(!NumberMaxValidator.validate(validators[key].value,value)){
                            error.push(validators[key].message);
                        };
                        break;

                    case DateSinceValidator.cons:
                        if(!DateSinceValidator.validate(validators[key].value,value)){
                            error.push(validators[key].message);
                        };
                        break;

                    case DateUntilValidator.cons:
                        if(!DateUntilValidator.validate(validators[key].value,value)){
                            error.push(validators[key].message);
                        };
                        break;

                    case EmailValidator.cons:
                        if(!EmailValidator.validate(value)){
                            error.push(validators[key].message);
                        };
                        break;

                    case RequiredValidator.cons:
                        if(!RequiredValidator.validate(value)){
                            error.push(validators[key].message);
                        };
                        break;

                    case StrLenMinValidator.cons:
                        if(!StrLenMinValidator.validate(validators[key].value,value)){
                            error.push(validators[key].message);
                        };
                        break;

                    case StrLenMaxValidator.cons:
                        if(!StrLenMaxValidator.validate(validators[key].value,value)){
                            error.push(validators[key].message);
                        };
                        break;

                    default:
                        //no op
                }
            }
        }

        return error;
    },

    getOptions: function () {
        var type = this.props.structure.inputType;
        if(type !== SelectOne.cons && type !== SelectMany.cons && type !== CheckboxGroup.cons && type !== RadioButtonGroup.cons) {
            return null;
        }

        var endpoint = this.props.structure.endpoint;

        if(endpoint !== null && endpoint !== undefined) {
            this.props.getOptions(endpoint)
                .with(this)
                .then(function (response) {
                    this.setState({options: response});
                });
        } else {
            this.setState({options: this.props.structure.options});
        }

    },

    renderInput: function () {

        switch (this.props.structure.inputType) {
            case TextInput.cons:
                return(
                    <TextInput id={this.props.structure.name} value={this.state.value} onChange={this.onChange}/>
                );
                break;

            case PasswordInput.cons:
                return(
                    <PasswordInput id={this.props.structure.name} value={this.state.value} onChange={this.onChange}/>
                );
                break;

            case TextFieldInput.cons:
                return(
                    <TextFieldInput id={this.props.structure.name} value={this.state.value} onChange={this.onChange}/>
                );
                break;

            case NumberInput.cons:
                return(
                    <NumberInput id={this.props.structure.name} value={this.state.value} onChange={this.onChange}/>
                );
                break;

            case DateInput.cons:
                return(
                    <DateInput id={this.props.structure.name} value={this.state.value} onChange={this.onChange}/>
                );
                break;

            case SelectOne.cons:
                return(
                    <SelectOne
                        id={this.props.structure.name}
                        value={this.state.value}
                        onChange={this.onChange}
                        options={this.state.options}
                    />
                );
                break;

            case SelectMany.cons:
                return(
                    <SelectMany
                        id={this.props.structure.name}
                        value={this.state.value}
                        onChange={this.onChange}
                        options={this.state.options}
                    />
                );
                break;

            case CheckboxGroup.cons:
                return(
                    <CheckboxGroup
                        id={this.props.structure.name}
                        value={this.state.value}
                        onChange={this.onChange}
                        options={this.state.options}
                    />
                );
                break;

            case RadioButtonGroup.cons:
                return(
                    <RadioButtonGroup
                        id={this.props.structure.name}
                        value={this.state.value}
                        onChange={this.onChange}
                        options={this.state.options}
                    />
                );
                break;

            default:
                return(
                    <div>Unknown input type: {this.props.structure.inputType}</div>
                );
        }
    },

    render: function () {

        var errorClassName = this.props.structure.inputErrorClass;
        var i = 0;
        var errors = this.state.error.map(function (message) {
            i++;
            return(
                <ErrorMessage key={"key"+i} className={errorClassName} message={message}/>
            );
        });

        return(
            <div className={this.props.structure.inputSurroundingClass}>
                <label htmlFor={this.props.structure.name}>{this.props.structure.label}</label>
                {this.renderInput()}
                {this.state.touched ? errors : null}
            </div>
        );

    },

    
});

module.exports = InputWrapper;