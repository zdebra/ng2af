var React = require("react");
var ReactDOM = require("react-dom");
var Form = require("./Form");
var when = require("when");

var formStructure = {
    "name": "newBookForm",
    "inputs": [{
        "name": "name",
        "placeholder": "Enter name of book",
        "label": "Name",
        "validators": {
            "regExp": {"value": ".*abc$", "message": "Must end with abc"},
            "strLenMin": {"value": 4, "message": "Must be longer than 3"}
        },
        "responseFormat": "VALUE",
        "inputType": "TEXT",
        "defaultValue": "def",
        "inputSurroundingClass": "pure-control-group",
        "inputErrorClass": "input-error"
    }, {
        "name": "description",
        "placeholder": "Describe the book in a few words",
        "label": "Description",
        "validators": {},
        "responseFormat": "VALUE",
        "inputType": "TEXTFIELD",
        "defaultValue": "def",
        "inputSurroundingClass": "pure-control-group",
        "inputErrorClass": "input-error"
    }, {
        "name": "author",
        "placeholder": "Choose author of this book",
        "label": "Author",
        "validators": {},
        "responseFormat": "VALUE",
        "endpoint": "/rest/book/author/options",
        "defaultValue": "Tonda",
        "inputType": "SELECT_ONE",
        "inputSurroundingClass": "pure-control-group",
        "inputErrorClass": "input-error"
    }, {
        "name": "isnb",
        "placeholder": "Enter ISNB number",
        "label": "ISNB",
        "validators": {"strLenMax": {"value": 8, "message": "You must enter max 8 chars"}},
        "responseFormat": "VALUE",
        "inputType": "TEXT",
        "defaultValue": "def",
        "inputSurroundingClass": "pure-control-group",
        "inputErrorClass": "input-error"
    }, {
        "name": "status",
        "placeholder": "Select whether this book is active",
        "label": "Status",
        "validators": {},
        "responseFormat": "VALUE",
        "options": {
            "active": {"@class": "boolean", "name": "active", "label": "Active", "value": true},
            "inactive": {"@class": "boolean", "name": "inactive", "label": "Inactive", "value": false}
        },
        "defaultValue": "active",
        "inputType": "RADIOBUTTON_GROUP",
        "inputSurroundingClass": "pure-control-group",
        "inputErrorClass": "input-error"
    }, {
        "name": "stores",
        "placeholder": "Choose stores where this book can be bought",
        "label": "Stores",
        "validators": {},
        "responseFormat": "VALUES",
        "endpoint": "/rest/place/store/options",
        "defaultValue": {"Tesco": 15},
        "inputType": "SELECT_MANY",
        "inputSurroundingClass": "pure-control-group",
        "inputErrorClass": "input-error"
    }, {
        "name": "price",
        "placeholder": "Enter price of book",
        "label": "Price",
        "validators": {
            "numberMax": {"value": 500, "message": "Price must be lower than 500"},
            "numberMin": {"value": 100, "message": "Price must be higher than 100"}
        },
        "responseFormat": "VALUE",
        "inputType": "NUMBER",
        "defaultValue": null,
        "inputSurroundingClass": "pure-control-group",
        "inputErrorClass": "input-error"
    }, {
        "name": "libraries",
        "placeholder": "Choose libraries where this book can be borrowed",
        "label": "Libraries",
        "validators": {},
        "responseFormat": "VALUES",
        "endpoint": "/rest/place/library/options",
        "defaultValue": {"NTK": 12, "Narodni knihovna": 13},
        "inputType": "SELECT_MANY",
        "inputSurroundingClass": "pure-control-group",
        "inputErrorClass": "input-error"
    }, {
        "name": "visibility",
        "placeholder": "Choose who see this book in system",
        "label": "Visibility",
        "validators": {},
        "responseFormat": "OPTIONS",
        "options": {
            "visitor": {"@class": "boolean", "name": "visitor", "label": "Visitor", "value": true},
            "editor": {"@class": "boolean", "name": "editor", "label": "Editor", "value": true},
            "manager": {"@class": "boolean", "name": "manager", "label": "Manager", "value": true}
        },
        "defaultValue": {"visitor": true, "editor": true, "manager": true},
        "inputType": "CHECKBOX_GROUP",
        "inputSurroundingClass": "pure-control-group",
        "inputErrorClass": "input-error"
    }, {
        "name": "dateAdded",
        "placeholder": "Enter a date when this book was added",
        "label": "Date Added",
        "validators": {"dateUntil": {"value": "2016-04-30", "message": "You can't add future dates"}},
        "responseFormat": "VALUE",
        "inputType": "DATE",
        "defaultValue": "2016-04-30",
        "inputSurroundingClass": "pure-control-group",
        "inputErrorClass": "input-error"
    }],
    "formClass": "pure-form pure-form-aligned",
    "buttonSurroundingClass": "pure-controls",
    "submitButtonClass": "pure-button pure-button-primary",
    "formErrorClass": "form-error"
};

function getOptions(endpoint) {
    // data mock..
    return when({
        "visitor": {"@class": "boolean", "name": "visitor", "label": "Visitor", "value": true},
        "editor": {"@class": "boolean", "name": "editor", "label": "Editor", "value": true},
        "manager": {"@class": "boolean", "name": "manager", "label": "Manager", "value": true}
    });
}

function onSubmit(data) {
    console.log("Form submitted", data);
}

ReactDOM.render(<Form structure={formStructure} onSubmit={onSubmit} getOptions={getOptions}/>, document.getElementById("app"));
