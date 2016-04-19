import {Component} from "angular2/core";
import {Input} from "angular2/core";
import {Control} from "angular2/common";
import {Validators} from "angular2/common";
import {FORM_DIRECTIVES} from "angular2/common";
import {ControlGroup} from "angular2/common";
import {FORM_PROVIDERS} from "angular2/common";
import {Inject} from "angular2/core";
import {CustomValidators} from "./validators.service.js";
import {Observable} from "rxjs/Observable";
import {Http, Response} from "angular2/http";


@Component({
    selector: 'dynamic-input',
    template: `<div [ngSwitch]="inp?.inputType" [attr.class]="inp?.inputSurroundingClass">
    <template ngSwitchWhen="TEXT">
        <label [attr.for]="inp?.name">{{inp?.label}}</label>
        <input [ngFormControl]="inpControl" [attr.id]="inp?.name" [attr.placeholder]="inp?.placeholder" type="text" [(ngModel)]="inp.defaultValue">
    </template>
    <template ngSwitchWhen="PASSWORD">
        <label [attr.for]="inp?.name">{{inp?.label}}</label>
        <input [ngFormControl]="inpControl" [attr.id]="inp?.name" type="password" [(ngModel)]="inp.defaultValue">
    </template>
    <template ngSwitchWhen="TEXTFIELD">
        <label [attr.for]="inp?.name">{{inp?.label}}</label>
        <textarea [ngFormControl]="inpControl" [attr.id]="inp?.name" [attr.placeholder]="inp?.placeholder" [(ngModel)]="inp.defaultValue"></textarea>
    </template>
    <template ngSwitchWhen="NUMBER">
        <label [attr.for]="inp?.name">{{inp?.label}}</label>
        <input [ngFormControl]="inpControl" [attr.id]="inp?.name" [attr.placeholder]="inp?.placeholder" type="number" [(ngModel)]="inp.defaultValue">
    </template>
    <template ngSwitchWhen="DATE">
        <label [attr.for]="inp?.name">{{inp?.label}}</label>
        <input [ngFormControl]="inpControl" [attr.id]="inp?.name" [attr.placeholder]="inp?.placeholder" type="date" [(ngModel)]="inp.defaultValue">
    </template>
    <template ngSwitchWhen="CHECKBOX_GROUP">
        <label>{{inp?.label}}</label>
        <template ngFor #opt [ngForOf]="arrayOfKeys">
            <label [attr.for]="inp.options[opt]?.name" class="pure-checkbox">
                <input type="checkbox" [attr.id]="inp.options[opt]?.name" [(ngModel)]="inp.defaultValue[inp.options[opt].name]">
                {{inp.options[opt]?.label}}
            </label>
        </template>
    </template>
    <template ngSwitchWhen="SELECT_MANY">
        <label [attr.for]="inp?.name">{{inp?.label}}</label>
        <select multiple [attr.id]="inp?.name" (change)="onChange($event,inp)">
            <option *ngFor="#o of arrayOfKeys" [value]="inp.options[o]?.name" [selected]="inp?.defaultValue[inp.options[o]?.name]">{{inp.options[o]?.label}}</option>
        </select>
    </template>
    <template ngSwitchWhen="SELECT_ONE">
        <label [attr.for]="inp?.name">{{inp?.label}}</label>
        <select [attr.id]="inp?.name" [(ngModel)]="inp.defaultValue">
            <option *ngFor="#o of arrayOfKeys" [value]="inp.options[o]?.name" >{{inp.options[o]?.label}}</option>
        </select>
    </template>
    <template ngSwitchWhen="RADIOBUTTON_GROUP">
        <label>{{inp?.label}}</label>
        <template ngFor #opt [ngForOf]="arrayOfKeys">
            <label [attr.for]="inp.options[opt]?.name" class="pure-radio">
                <input type="radio" [attr.name]="inp.name" [attr.id]="inp.options[opt]?.name" [value]="inp.options[opt]?.name"
                       [checked]="inp.defaultValue === inp.options[opt].name" (click)="inp.defaultValue = inp.options[opt].name">
                {{inp.options[opt]?.label}}
            </label>
        </template>
    </template>
    <template ngSwitchDefault>Unsupported input type: {{inp?.inputType}}<br></template>
    <div *ngIf="inpControl.dirty || form.touched" [attr.class]="inp?.inputErrorClass">
        <span [hidden]="inpControl.valid" class="ui error message">{{inp?.label}} invalid:</span>
        <span [hidden]="!inpControl.hasError('regExp')">{{inp?.validators?.regExp?.message}}</span>
        <span [hidden]="!inpControl.hasError('required')">{{inp?.validators?.required?.message}}</span>
        <span [hidden]="!inpControl.hasError('dateSince')">{{inp?.validators?.dateSince?.message}}</span>
        <span [hidden]="!inpControl.hasError('dateUntil')">{{inp?.validators?.dateUntil?.message}}</span>
        <span [hidden]="!inpControl.hasError('numberMax')">{{inp?.validators?.numberMax?.message}}</span>
        <span [hidden]="!inpControl.hasError('numberMin')">{{inp?.validators?.numberMin?.message}}</span>
        <span [hidden]="!inpControl.hasError('strLenMin')">{{inp?.validators?.strLenMin?.message}}</span>
        <span [hidden]="!inpControl.hasError('strLenMax')">{{inp?.validators?.strLenMax?.message}}</span>
        <span [hidden]="!inpControl.hasError('email')">{{inp?.validators?.email?.message}}</span>
    </div>
</div>`,
    directives: [FORM_DIRECTIVES],
    providers: [CustomValidators]
})
export class DynamicInputComponent {

    @Input('inp') public inp:any;
    @Input('getOptions') public getOptions:Function; // the function returning Observable<Response>
    @Input('form') public form:ControlGroup;

    public arrayOfKeys;

    public inpControl:Control = new Control("");

    constructor(private _customValidators:CustomValidators, private _http:Http){
    }

    public ngAfterContentInit():void {
        if(this.inp.endpoint) {
            this.getOptions(this.inp.endpoint).subscribe(
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