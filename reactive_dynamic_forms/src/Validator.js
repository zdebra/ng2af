// return false when validation fails
var Validator = {

    RegExp: {
        cons: "regExp",
        validate: function (pattern, text) {
            var regExp = new RegExp(pattern);
            return regExp.test(text);
        }
    },

    NumberMax: {
        cons: "numberMax",
        validate: function (max, number) {
            return(number <= max);
        }
    },

    NumberMin: {
        cons: "numberMin",
        validate: function (max, number) {
            return(number >= max);
        }
    },

    DateSince: {
        cons: "dateSince",
        validate: function (limit, inputDate) {
            limit = new Date(limit);
            inputDate = new Date(date);
            return(inputDate >= limit);
        }
    },

    DateUntil: {
        cons: "dateUntil",
        validate: function (limit, inputDate) {
            limit = new Date(limit);
            inputDate = new Date(inputDate);
            return(inputDate <= limit);
        }
    },

    Email: {
        cons: "email",
        validate: function (input) {
            var pattern = "[-a-z0-9~!$%^&*_=+}{\'?]+(\.[-a-z0-9~!$%^&*_=+}{\'?]+)*@([a-z0-9_][-a-z0-9_]*(\.[-a-z0-9_]+)*\.(aero|arpa|biz|com|coop|edu|gov|info|int|mil|museum|name|net|org|pro|travel|mobi|[a-z][a-z])|([0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}))(:[0-9]{1,5})?$";
            var regExp = new RegExp(pattern);
            return regExp.test(input);
        }
    },

    Required: {
        cons: "required",
        validate: function (input) {
            return (input !== null && input !== "undefined");
        }
    },

    StrLenMin: {
        cons: "strLenMin",
        validate: function (limit, input) {
            return (input.length >= limit);
        }
    },

    StrLenMax: {
        cons: "strLenMax",
        validate: function (limit, input) {
            return (input.length <= limit);
        }
    }
};

module.exports = Validator;