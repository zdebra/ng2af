import {Component} from "angular2/core";
import {Input} from "angular2/core";
import {FormService} from "../form.service";
import {Control} from "angular2/common";
import {Validators} from "angular2/common";
import {FORM_DIRECTIVES} from "angular2/common";
import {ControlGroup} from "angular2/common";
import {FORM_PROVIDERS} from "angular2/common";
import {Inject} from "angular2/core";
import {CustomValidators} from "./validators.service";

@Component({
    selector: 'dynamic-input',
    templateUrl: 'view/dynamicInput.html',
    directives: [FORM_DIRECTIVES],
    providers: [CustomValidators]
})
export class DynamicInputComponent {

    @Input('inp') public inp:any;
    @Input('form') public form:ControlGroup;

    public arrayOfKeys;

    public inpControl:Control = new Control("");

    constructor(private _form:FormService, private _customValidators:CustomValidators){
    }

    public ngAfterContentInit():void {
        if(this.inp.endpoint) {
            this._form.getOptionsFromEndpoint(this.inp.endpoint).subscribe(
                data => {
                    this.inp.options = data.json();
                    this.arrayOfKeys = Object.keys(this.inp.options);
                },
                err => console.log(err)
            )
        }



        this.form.controls[this.inp.name] = this.inpControl;
        this.inpControl.validator = this.composeValidators(this.inp.validators);
        this.inpControl.updateValueAndValidity();
        if(this.inp.options) {
            this.arrayOfKeys = Object.keys(this.inp.options);
        }


    }

    private composeValidators(val:any):any {
        var vals = [];
        if(val['strLenMin']) {
            vals.push(Validators.minLength(val['strLenMin'].value));
        }
        if(val['strLenMax']) {
            vals.push(Validators.maxLength(val['strLenMax'].value));
        }
        if(val['required'] && val['required'].value === true) {
            vals.push(Validators.required);
        }
        if(val['regExp']) {
            vals.push(this._customValidators.regExp(val['regExp'].value));
        }
        if(val['dateSince']) {
            vals.push(this._customValidators.dateSince(val['dateSince'].value));
        }
        if(val['dateUntil']) {
            vals.push(this._customValidators.dateUntil(val['dateUntil'].value));
        }
        if(val['numberMax']) {
            vals.push(this._customValidators.numberMax(val['numberMax'].value));
        }
        if(val['numberMin']) {
            vals.push(this._customValidators.numberMin(val['numberMin'].value));
        }
        if(val['email']) {
            vals.push(this._customValidators.email());
        }

        return Validators.compose(vals);

    }


    public onChange(event:any, inp:any):void {
        event.preventDefault();

        inp.defaultValue = {};
        for(var option of event.srcElement.children) {
            if(option.selected===true) {
                inp.defaultValue[option.value] = option.value;
            }
        }

    }


}