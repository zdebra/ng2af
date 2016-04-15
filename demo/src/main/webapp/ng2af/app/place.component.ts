import {Component} from "angular2/core";
import {FormService} from "./form.service";
import {DynamicFormComponent} from "./dynamic_forms/form.component";
import {Router} from "angular2/router";
import {PlaceService} from "./place.service";
@Component({
    selector: 'place',
    template: `<h2>Create place</h2><dynamic-form [structure]="formData" (submit)="onSubmit($event)"></dynamic-form>`,
    providers: [FormService, PlaceService],
    directives: [DynamicFormComponent]

})
export class PlaceComponent {

    public formData:Object;

    constructor(private _place:PlaceService ,private _form:FormService, private _router:Router) {
        this.getFormStructure();
    }


    public onSubmit(output:any) {
        var className: string = output.constructor.toString().match(/\w+/g)[1];
        if(className=='Event') {
            let event:Event = <Event> output;
            event.preventDefault();
        } else {
            // for some reason id must be in first position for backend deserializer
            var newOutput:any = {};
            newOutput['id'] = -1;
            for(let key in output) {
                newOutput[key] = output[key];
            }
            this._place.createPlace(newOutput).subscribe(
                data => {
                    this._router.navigate(["Home"]);
                },
                err => console.log(err)
            );
        }

    }

    private getFormStructure() {

        this._form.getFormStructure("newPlaceForm").subscribe(
            data => {
                this.formData = data.json();
            },
            err => console.log(err)
        )

    }

}