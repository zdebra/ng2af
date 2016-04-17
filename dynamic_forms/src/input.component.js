System.register(["angular2/core", "angular2/common", "./validators.service.js", "angular2/http"], function(exports_1) {
    var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
        var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
        if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
        else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
        return c > 3 && r && Object.defineProperty(target, key, r), r;
    };
    var __metadata = (this && this.__metadata) || function (k, v) {
        if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
    };
    var core_1, core_2, common_1, common_2, common_3, common_4, validators_service_js_1, http_1;
    var DynamicInputComponent;
    return {
        setters:[
            function (core_1_1) {
                core_1 = core_1_1;
                core_2 = core_1_1;
            },
            function (common_1_1) {
                common_1 = common_1_1;
                common_2 = common_1_1;
                common_3 = common_1_1;
                common_4 = common_1_1;
            },
            function (validators_service_js_1_1) {
                validators_service_js_1 = validators_service_js_1_1;
            },
            function (http_1_1) {
                http_1 = http_1_1;
            }],
        execute: function() {
            DynamicInputComponent = (function () {
                function DynamicInputComponent(_customValidators, _http) {
                    this._customValidators = _customValidators;
                    this._http = _http;
                    this.inpControl = new common_1.Control("");
                }
                DynamicInputComponent.prototype.ngAfterContentInit = function () {
                    var _this = this;
                    if (this.inp.endpoint) {
                        this.getOptions(this.inp.endpoint).subscribe(function (data) {
                            _this.inp.options = data.json();
                            _this.arrayOfKeys = Object.keys(_this.inp.options);
                        }, function (err) { return console.log(err); });
                    }
                    this.form.controls[this.inp.name] = this.inpControl;
                    this.inpControl.validator = this.composeValidators(this.inp.validators);
                    this.inpControl.updateValueAndValidity();
                    if (this.inp.options) {
                        this.arrayOfKeys = Object.keys(this.inp.options);
                    }
                };
                DynamicInputComponent.prototype.composeValidators = function (val) {
                    var vals = [];
                    if (val['strLenMin']) {
                        vals.push(common_2.Validators.minLength(val['strLenMin'].value));
                    }
                    if (val['strLenMax']) {
                        vals.push(common_2.Validators.maxLength(val['strLenMax'].value));
                    }
                    if (val['required'] && val['required'].value === true) {
                        vals.push(common_2.Validators.required);
                    }
                    if (val['regExp']) {
                        vals.push(this._customValidators.regExp(val['regExp'].value));
                    }
                    if (val['dateSince']) {
                        vals.push(this._customValidators.dateSince(val['dateSince'].value));
                    }
                    if (val['dateUntil']) {
                        vals.push(this._customValidators.dateUntil(val['dateUntil'].value));
                    }
                    if (val['numberMax']) {
                        vals.push(this._customValidators.numberMax(val['numberMax'].value));
                    }
                    if (val['numberMin']) {
                        vals.push(this._customValidators.numberMin(val['numberMin'].value));
                    }
                    if (val['email']) {
                        vals.push(this._customValidators.email());
                    }
                    return common_2.Validators.compose(vals);
                };
                DynamicInputComponent.prototype.onChange = function (event, inp) {
                    event.preventDefault();
                    inp.defaultValue = {};
                    for (var _i = 0, _a = event.srcElement.children; _i < _a.length; _i++) {
                        var option = _a[_i];
                        if (option.selected === true) {
                            inp.defaultValue[option.value] = option.value;
                        }
                    }
                };
                __decorate([
                    core_2.Input('inp'), 
                    __metadata('design:type', Object)
                ], DynamicInputComponent.prototype, "inp", void 0);
                __decorate([
                    core_2.Input('getOptions'), 
                    __metadata('design:type', Function)
                ], DynamicInputComponent.prototype, "getOptions", void 0);
                __decorate([
                    // the function returning Observable<Response>
                    core_2.Input('form'), 
                    __metadata('design:type', common_4.ControlGroup)
                ], DynamicInputComponent.prototype, "form", void 0);
                DynamicInputComponent = __decorate([
                    core_1.Component({
                        selector: 'dynamic-input',
                        template: "<div [ngSwitch]=\"inp?.inputType\" class=\"pure-control-group\">\n    <template ngSwitchWhen=\"TEXT\">\n        <label [attr.for]=\"inp?.name\">{{inp?.label}}</label>\n        <input [ngFormControl]=\"inpControl\" [attr.id]=\"inp?.name\" [attr.placeholder]=\"inp?.placeholder\" type=\"text\" [(ngModel)]=\"inp.defaultValue\">\n    </template>\n    <template ngSwitchWhen=\"PASSWORD\">\n        <label [attr.for]=\"inp?.name\">{{inp?.label}}</label>\n        <input [ngFormControl]=\"inpControl\" [attr.id]=\"inp?.name\" type=\"password\" [(ngModel)]=\"inp.defaultValue\">\n    </template>\n    <template ngSwitchWhen=\"TEXTFIELD\">\n        <label [attr.for]=\"inp?.name\">{{inp?.label}}</label>\n        <textarea [ngFormControl]=\"inpControl\" [attr.id]=\"inp?.name\" [attr.placeholder]=\"inp?.placeholder\" [(ngModel)]=\"inp.defaultValue\"></textarea>\n    </template>\n    <template ngSwitchWhen=\"NUMBER\">\n        <label [attr.for]=\"inp?.name\">{{inp?.label}}</label>\n        <input [ngFormControl]=\"inpControl\" [attr.id]=\"inp?.name\" [attr.placeholder]=\"inp?.placeholder\" type=\"number\" [(ngModel)]=\"inp.defaultValue\">\n    </template>\n    <template ngSwitchWhen=\"DATE\">\n        <label [attr.for]=\"inp?.name\">{{inp?.label}}</label>\n        <input [ngFormControl]=\"inpControl\" [attr.id]=\"inp?.name\" [attr.placeholder]=\"inp?.placeholder\" type=\"date\" [(ngModel)]=\"inp.defaultValue\">\n    </template>\n    <template ngSwitchWhen=\"CHECKBOX_GROUP\">\n        <label>{{inp?.label}}</label>\n        <template ngFor #opt [ngForOf]=\"arrayOfKeys\">\n            <label [attr.for]=\"inp.options[opt]?.name\" class=\"pure-checkbox\">\n                <input type=\"checkbox\" [attr.id]=\"inp.options[opt]?.name\" [(ngModel)]=\"inp.defaultValue[inp.options[opt].name]\">\n                {{inp.options[opt]?.label}}\n            </label>\n        </template>\n    </template>\n    <template ngSwitchWhen=\"SELECT_MANY\">\n        <label [attr.for]=\"inp?.name\">{{inp?.label}}</label>\n        <select multiple [attr.id]=\"inp?.name\" (change)=\"onChange($event,inp)\">\n            <option *ngFor=\"#o of arrayOfKeys\" [value]=\"inp.options[o]?.name\" [selected]=\"inp?.defaultValue[inp.options[o]?.name]\">{{inp.options[o]?.label}}</option>\n        </select>\n    </template>\n    <template ngSwitchWhen=\"SELECT_ONE\">\n        <label [attr.for]=\"inp?.name\">{{inp?.label}}</label>\n        <select [attr.id]=\"inp?.name\" [(ngModel)]=\"inp.defaultValue\">\n            <option *ngFor=\"#o of arrayOfKeys\" [value]=\"inp.options[o]?.name\" >{{inp.options[o]?.label}}</option>\n        </select>\n    </template>\n    <template ngSwitchWhen=\"RADIOBUTTON_GROUP\">\n        <label>{{inp?.label}}</label>\n        <template ngFor #opt [ngForOf]=\"arrayOfKeys\">\n            <label [attr.for]=\"inp.options[opt]?.name\" class=\"pure-radio\">\n                <input type=\"radio\" [attr.name]=\"inp.name\" [attr.id]=\"inp.options[opt]?.name\" [value]=\"inp.options[opt]?.name\"\n                       [checked]=\"inp.defaultValue === inp.options[opt].name\" (click)=\"inp.defaultValue = inp.options[opt].name\">\n                {{inp.options[opt]?.label}}\n            </label>\n        </template>\n    </template>\n    <template ngSwitchDefault>Unsupported input type: {{inp?.inputType}}<br></template>\n    <div *ngIf=\"inpControl.dirty || form.touched\">\n        <span [hidden]=\"inpControl.valid\" class=\"ui error message\">{{inp?.label}} invalid:</span>\n        <span [hidden]=\"!inpControl.hasError('regExp')\">{{inp?.validators?.regExp?.message}}</span>\n        <span [hidden]=\"!inpControl.hasError('required')\">{{inp?.validators?.required?.message}}</span>\n        <span [hidden]=\"!inpControl.hasError('dateSince')\">{{inp?.validators?.dateSince?.message}}</span>\n        <span [hidden]=\"!inpControl.hasError('dateUntil')\">{{inp?.validators?.dateUntil?.message}}</span>\n        <span [hidden]=\"!inpControl.hasError('numberMax')\">{{inp?.validators?.numberMax?.message}}</span>\n        <span [hidden]=\"!inpControl.hasError('numberMin')\">{{inp?.validators?.numberMin?.message}}</span>\n        <span [hidden]=\"!inpControl.hasError('strLenMin')\">{{inp?.validators?.strLenMin?.message}}</span>\n        <span [hidden]=\"!inpControl.hasError('strLenMax')\">{{inp?.validators?.strLenMax?.message}}</span>\n        <span [hidden]=\"!inpControl.hasError('email')\">{{inp?.validators?.email?.message}}</span>\n    </div>\n</div>",
                        directives: [common_3.FORM_DIRECTIVES],
                        providers: [validators_service_js_1.CustomValidators]
                    }), 
                    __metadata('design:paramtypes', [(typeof (_a = typeof validators_service_js_1.CustomValidators !== 'undefined' && validators_service_js_1.CustomValidators) === 'function' && _a) || Object, http_1.Http])
                ], DynamicInputComponent);
                return DynamicInputComponent;
                var _a;
            })();
            exports_1("DynamicInputComponent", DynamicInputComponent);
        }
    }
});
//# sourceMappingURL=input.component.js.map