import {Component} from "angular2/core";
import {FormService} from "./form.service";
import {DynamicFormComponent} from "./dynamic_forms/form.component";
import {Router} from "angular2/router";
import {UserService} from "./user.service";
@Component({
    selector: 'user',
    template: `<h2>Create user</h2><dynamic-form [structure]="formData" (submit)="onSubmit($event)"></dynamic-form>`,
    providers: [FormService, UserService],
    directives: [DynamicFormComponent]

})
export class UserComponent {

    public formData:Object;

    constructor(private _user:UserService ,private _form:FormService, private _router:Router) {
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
            this._user.createUser(newOutput).subscribe(
                data => {
                    this._router.navigate(["Home"]);
                },
                err => console.log(err)
            );
        }

    }

    private getFormStructure() {

        this._form.getFormStructure("newUserForm").subscribe(
            data => {
                this.formData = data.json();
            },
            err => console.log(err)
        )

    }

}