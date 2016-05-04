System.register(["angular2/core", "./input.component.js", "angular2/common"], function(exports_1, context_1) {
    "use strict";
    var __moduleName = context_1 && context_1.id;
    var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
        var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
        if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
        else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
        return c > 3 && r && Object.defineProperty(target, key, r), r;
    };
    var __metadata = (this && this.__metadata) || function (k, v) {
        if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
    };
    var core_1, core_2, input_component_js_1, core_3, core_4, common_1, common_2;
    var DynamicFormComponent;
    return {
        setters:[
            function (core_1_1) {
                core_1 = core_1_1;
                core_2 = core_1_1;
                core_3 = core_1_1;
                core_4 = core_1_1;
            },
            function (input_component_js_1_1) {
                input_component_js_1 = input_component_js_1_1;
            },
            function (common_1_1) {
                common_1 = common_1_1;
                common_2 = common_1_1;
            }],
        execute: function() {
            DynamicFormComponent = (function () {
                function DynamicFormComponent(_fb) {
                    this._fb = _fb;
                    this.submit = new core_4.EventEmitter();
                    this.myForm = this._fb.group({});
                }
                DynamicFormComponent.prototype.onSubmit = function (event) {
                    this.myForm.markAsTouched();
                    this.myForm.updateValueAndValidity();
                    if (this.myForm.valid) {
                        var data = {};
                        for (var i = 0; i < this.structure.inputs.length; i++) {
                            if (this.structure.inputs[i].options || this.structure.inputs[i].endpoint) {
                                switch (this.structure.inputs[i].responseFormat) {
                                    case "VALUES":
                                        data[this.structure.inputs[i].name] = [];
                                        for (var itemName in this.structure.inputs[i].defaultValue) {
                                            data[this.structure.inputs[i].name].push(this.structure.inputs[i].options[itemName].value);
                                        }
                                        break;
                                    case "NAMES":
                                        data[this.structure.inputs[i].name] = [];
                                        for (var itemName in this.structure.inputs[i].defaultValue) {
                                            data[this.structure.inputs[i].name].push(this.structure.inputs[i].options[itemName].name);
                                        }
                                        break;
                                    case "OPTIONS":
                                        data[this.structure.inputs[i].name] = [];
                                        for (var itemName in this.structure.inputs[i].defaultValue) {
                                            var it = this.structure.inputs[i].options[itemName];
                                            it['value'] = this.structure.inputs[i].defaultValue[itemName];
                                            data[this.structure.inputs[i].name].push(it);
                                        }
                                        break;
                                    case "VALUE":
                                        if (this.structure.inputs[i].options[this.structure.inputs[i].defaultValue]) {
                                            data[this.structure.inputs[i].name] = this.structure.inputs[i].options[this.structure.inputs[i].defaultValue].value;
                                        }
                                        break;
                                    case "NAME":
                                        if (this.structure.inputs[i].options[this.structure.inputs[i].defaultValue]) {
                                            data[this.structure.inputs[i].name] = this.structure.inputs[i].options[this.structure.inputs[i].defaultValue].name;
                                        }
                                        break;
                                    case "OPTION":
                                        if (this.structure.inputs[i].options[this.structure.inputs[i].defaultValue]) {
                                            data[this.structure.inputs[i].name] = this.structure.inputs[i].options[this.structure.inputs[i].defaultValue];
                                            data[this.structure.inputs[i].name]['value'] = this.structure.inputs[i].options[this.structure.inputs[i].defaultValue].value;
                                        }
                                        break;
                                }
                            }
                            else {
                                data[this.structure.inputs[i].name] = this.structure.inputs[i].defaultValue;
                            }
                        }
                        this.submit.emit(data);
                    }
                };
                __decorate([
                    core_2.Input('structure'), 
                    __metadata('design:type', Object)
                ], DynamicFormComponent.prototype, "structure", void 0);
                __decorate([
                    core_2.Input('optionsFnc'), 
                    __metadata('design:type', Function)
                ], DynamicFormComponent.prototype, "optionsFnc", void 0);
                __decorate([
                    // the function returning Observable<Response>
                    core_3.Output('submit'), 
                    __metadata('design:type', core_4.EventEmitter)
                ], DynamicFormComponent.prototype, "submit", void 0);
                DynamicFormComponent = __decorate([
                    core_1.Component({
                        selector: 'dynamic-form',
                        template: "\n            <form novalidate [attr.name]=\"structure?.name\" [attr.class]=\"structure?.formClass\" (ngSubmit)=\"onSubmit($event)\" [ngFormModel]=\"myForm\">\n                <fieldset>\n                    <dynamic-input *ngFor=\"#inp of structure?.inputs\" [inp]=\"inp\" [form]=\"myForm\" [getOptions]=\"optionsFnc\"></dynamic-input>\n                </fieldset>\n                <div [attr.class]=\"structure?.buttonSurroundingClass\">\n                    <button type=\"submit\" [attr.class]=\"structure?.submitButtonClass\">Submit</button>\n                </div>\n\n                <div *ngIf=\"!myForm.valid\" [attr.class]=\"structure?.formErrorClass\">Form is invalid</div>\n            </form>",
                        directives: [input_component_js_1.DynamicInputComponent, common_2.FORM_DIRECTIVES]
                    }), 
                    __metadata('design:paramtypes', [common_1.FormBuilder])
                ], DynamicFormComponent);
                return DynamicFormComponent;
            }());
            exports_1("DynamicFormComponent", DynamicFormComponent);
        }
    }
});
//# sourceMappingURL=form.component.js.map