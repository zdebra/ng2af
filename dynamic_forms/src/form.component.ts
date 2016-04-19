import {Component} from "angular2/core";
import {Input} from "angular2/core";
import {DynamicInputComponent} from "./input.component.js";
import {Output} from "angular2/core";
import {EventEmitter} from "angular2/core";
import {ControlGroup} from "angular2/common";
import {FormBuilder} from "angular2/common";
import {FORM_DIRECTIVES} from "angular2/common";

@Component({
    selector: 'dynamic-form',
    template: `
            <form novalidate [attr.name]="structure?.name" [attr.class]="structure?.formClass" (ngSubmit)="onSubmit($event)" [ngFormModel]="myForm">
                <fieldset>
                    <dynamic-input *ngFor="#inp of structure?.inputs" [inp]="inp" [form]="myForm" [getOptions]="optionsFnc"></dynamic-input>
                </fieldset>
                <div [attr.class]="structure?.buttonSurroundingClass">
                    <button type="submit" [attr.class]="structure?.submitButtonClass">Submit</button>
                </div>

                <div *ngIf="!myForm.valid" [attr.class]="structure?.formErrorClass">Form is invalid</div>
            </form>`,
    directives: [DynamicInputComponent, FORM_DIRECTIVES]
})
export class DynamicFormComponent {

    @Input('structure') public structure:any;
    @Input('optionsFnc') public optionsFnc:Function; // the function returning Observable<Response>
    @Output('submit') public submit: EventEmitter<any> = new EventEmitter();

    public myForm:ControlGroup;

    constructor(private _fb:FormBuilder) {
        this.myForm = this._fb.group({});
    }

    public onSubmit(event:Event):void {

        this.myForm.markAsTouched();
        this.myForm.updateValueAndValidity();

        if(this.myForm.valid) {
            var data:Object = {};
            for (let i = 0; i < this.structure.inputs.length; i++) {
                if (this.structure.inputs[i].options || this.structure.inputs[i].endpoint) {
                    switch (this.structure.inputs[i].responseFormat) {
                        case "VALUES":
                            data[this.structure.inputs[i].name] = [];
                            for (let itemName in this.structure.inputs[i].defaultValue) {
                                data[this.structure.inputs[i].name].push(this.structure.inputs[i].options[itemName].value);
                            }
                            break;
                        case "NAMES":
                            data[this.structure.inputs[i].name] = [];
                            for (let itemName in this.structure.inputs[i].defaultValue) {
                                data[this.structure.inputs[i].name].push(this.structure.inputs[i].options[itemName].name);
                            }
                            break;
                        case "OPTIONS":
                            data[this.structure.inputs[i].name] = [];
                            for (let itemName in this.structure.inputs[i].defaultValue) {
                                let it = this.structure.inputs[i].options[itemName];
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

                } else {
                    data[this.structure.inputs[i].name] = this.structure.inputs[i].defaultValue;
                }
            }

            this.submit.emit(data);
        }
    }
}