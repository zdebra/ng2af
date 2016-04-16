import {Component} from "angular2/core";
import {Book} from "./book.model";
import {BookService} from "./book.service";
import {Router} from "angular2/router";
import {FormService} from "./form.service";
import {Json} from "angular2/src/facade/lang";
import {DynamicFormComponent} from "ng2dynamicform/src/form.component";

@Component({
    selector: 'book',
    template: `<h2>Create book</h2><dynamic-form [structure]="formData" (submit)="onSubmit($event)" [optionsFnc]="_form.getOptionsFromEndpoint"></dynamic-form>`,
    providers: [BookService, FormService],
    directives: [DynamicFormComponent]
})
export class BookComponent {

    public formData:Object;

    constructor(private _book:BookService, public _form:FormService, private _router:Router) {
        this.getFormStructure();
    }

    public onSubmit(output:any) {
        var className: string = output.constructor.toString().match(/\w+/g)[1];
        if(className=='Event') {
            let event:Event = <Event> output;
            event.preventDefault();
        } else {
            output['id'] = -1;
            this._book.createBook(output).subscribe(
                data => {
                    this._router.navigate(["Home"]);
                },
                err => console.log(err)
            );
        }

    }

    private getFormStructure() {

        this._form.getFormStructure("newBookForm").subscribe(
            data => {
                this.formData = data.json();
            },
            err => console.log(err)
        )

    }

}
