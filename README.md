# Angular 2 AspectFaces implementation
### What is AspectFaces?
AspectFaces is a way how to save time on UI development. You easily define your
forms on the backend side of the application and reduce information restating,
coupling and ease the maintenance.

You can find more informations and original implementation as a JSF ([Java Server Faces](http://www.oracle.com/technetwork/java/javaee/javaserverfaces-139869.html)) extension here at <http://www.aspectfaces.com>.

### How does it work?
##### Server side
Create a form. You can choose from wide palette of inputs like `RadioButtonGroupInput`, `SelectManyBasicOptionsInput` and many more. You can specify who will see your input by `role` attribute. Next, you want your form to be a rich, add some validators. Finally, create form using previously injected `FormBuilder`.  
```java
Input name =new TextInput("name","Enter name of book","Name",roles,"default value");
Input price = new NumberInput("price", "Enter price of book", "Price",roles);

name.addValidator(new RegExpValidator("Must end with abc", ".*abc$"));
price.addValidator(new MaxNumberValidator("Price must be lower than 500",500));
price.addValidator(new MinNumberValidator("Price must be higher than 100",100));

Collection<Input> inputs = new ArrayList<>(2);
inputs.add(name);
inputs.add(price);

Form form = new Form("awesomeFormName");
form.setInputs(inputs);
formBuilder.createForm(form);
```
Note that this task runs only once when you start your server. Forms are fully persisted. This means you can circumvent this utilizing direct access to database.

Then, when you want to handle a http request for this form, you simply build form like this:
```java
Form form = formBuilder.buildForm("formName","roleName");
```
Which returns non-persisted instance of form created specially for given role just ready to be serialized to JSON and sent back. This gave you powerful tool when creating sizeable forms and want to your system roles have different view. You can see more real world example in demo.

##### Client side
This is how your Angular 2 component might looks like.
```typescript
import {Component} from "angular2/core";
import {FormService} from "./form.service";
import {DynamicFormComponent} from "ng2dynamicform/src/form.component";

@Component({
    selector: 'book',
    template: `<h2>Create book</h2><dynamic-form [structure]="formData" (submit)="onSubmit($event)" [optionsFnc]="_form.getOptionsFromEndpoint"></dynamic-form>`,
    providers: [FormService],
    directives: [DynamicFormComponent]
})
export class BookComponent {

    public formData:Object;

    constructor(public _form:FormService) {
        this.getFormStructure();
    }

    private getFormStructure() {
        this._form.getFormStructure("newBookForm").subscribe(
            data => {
                this.formData = data.json();
            },
            err => console.log(err)
        )
    }

    ...

}
```
`BookComponent` simply obtains data through `FormService` to fill up `dynamic-form`.
