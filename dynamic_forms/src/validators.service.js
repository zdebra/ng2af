System.register(["angular2/core"], function(exports_1) {
    var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
        var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
        if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
        else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
        return c > 3 && r && Object.defineProperty(target, key, r), r;
    };
    var __metadata = (this && this.__metadata) || function (k, v) {
        if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
    };
    var core_1;
    var CustomValidators;
    return {
        setters:[
            function (core_1_1) {
                core_1 = core_1_1;
            }],
        execute: function() {
            CustomValidators = (function () {
                function CustomValidators() {
                }
                CustomValidators.prototype.regExp = function (pattern) {
                    return function (c) {
                        if (!c.value.match(pattern)) {
                            return { regExp: true };
                        }
                    };
                };
                CustomValidators.prototype.numberMax = function (value) {
                    return function (c) {
                        if (c.value > value) {
                            return { numberMax: true };
                        }
                    };
                };
                CustomValidators.prototype.numberMin = function (value) {
                    return function (c) {
                        if (c.value < value) {
                            return { numberMin: true };
                        }
                    };
                };
                CustomValidators.prototype.dateSince = function (value) {
                    return function (c) {
                        var date = new Date(value);
                        var inputDate = new Date(c.value);
                        if (inputDate < date) {
                            return { dateSince: true };
                        }
                    };
                };
                CustomValidators.prototype.dateUntil = function (value) {
                    return function (c) {
                        var date = new Date(value);
                        var inputDate = new Date(c.value);
                        if (inputDate > date) {
                            return { dateUntil: true };
                        }
                    };
                };
                CustomValidators.prototype.email = function () {
                    var pattern = "[-a-z0-9~!$%^&*_=+}{\'?]+(\.[-a-z0-9~!$%^&*_=+}{\'?]+)*@([a-z0-9_][-a-z0-9_]*(\.[-a-z0-9_]+)*\.(aero|arpa|biz|com|coop|edu|gov|info|int|mil|museum|name|net|org|pro|travel|mobi|[a-z][a-z])|([0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}))(:[0-9]{1,5})?$";
                    return function (c) {
                        if (!c.value.match(pattern)) {
                            return { regExp: true };
                        }
                    };
                };
                CustomValidators = __decorate([
                    core_1.Injectable(), 
                    __metadata('design:paramtypes', [])
                ], CustomValidators);
                return CustomValidators;
            })();
            exports_1("CustomValidators", CustomValidators);
        }
    }
});
//# sourceMappingURL=validators.service.js.map