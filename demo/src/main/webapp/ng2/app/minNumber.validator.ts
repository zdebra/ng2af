import {Directive, provide, forwardRef} from 'angular2/core';
import {NG_VALIDATORS} from 'angular2/common';
import {Control} from "angular2/common";
import {Input} from "angular2/core";

@Directive({
    selector: '[minNumber][ngControl]',
    providers: [
        provide(NG_VALIDATORS, {
            useExisting: forwardRef(()=>MinNumberValidator),
            multi:true
        })
    ]
})
export class MinNumberValidator {
    @Input('minNumber') public value: string;

    public validate(c:Control):{[key:string]:any} {
        if(c.value < Number(this.value)) {
            return {numberMin:true}
        }
    }

}

@Directive({
    selector: '[maxNumber][ngControl]',
    providers: [
        provide(NG_VALIDATORS, {
            useExisting: forwardRef(()=>MaxNumberValidator),
            multi:true
        })
    ]
})
export class MaxNumberValidator {
    @Input('maxNumber') public value: string;

    public validate(c:Control):{[key:string]:any} {
        if(c.value > Number(this.value)) {
            return {numberMin:true}
        }
    }

}