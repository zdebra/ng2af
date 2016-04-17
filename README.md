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
`BookComponent` simply obtains data through `FormService` to fill up `dynamic-form` which is where form is rendered. You can see more in the [demo](demo/src/main/webapp/ng2af/).

### How to run the demo?
1. Make sure you have installed Java EE7 application server. I am using [Wildfly 10.0.0.Final](http://wildfly.org/downloads/).

2. Clone the repository and either hook up this rutine to your IDE, or navigate to demo folder and run `mvn install` here. Then you need to get all frontend dependencies.

3. There are two Angular 2 apps in `demo/src/main/webapp/`. First, in the `ng2` folder is without usage of DynamicForms made for comparsion purposes and the second, in the `ng2af` folder, where prototype app with usage of DynamicForms is. Run `npm install` in folder of prototype you want to use, or in both of them. Make sure you run `npm link ../../../../../dynamic_forms` to link local library in `ng2af` folder before processing `npm install`.

5. Next, you need to transpile typescript files to javascript. You can run transpiler in client app root directory, for example `node-typesript`. This watcher will look up for file changes and transpile to javascript. I used automatic build-in transpiler in Intellij IDEA.

6. When you have both server and client app dependencies it's time for start the app. At first, run `mvn package` to create deployeable WAR package. Then, use your application server to deploy the app. For wildfly's CLI it's `deploy /path/to/war/file.war`. Or use your IDE to handle this.

7. You should see app running on url where you deployed the app.

### Documentation
* [Input documentation](af/inputs.md)
* [Option documentation](af/options.md)
* [Validators documentation](af/validators.md)
* [DynamicForms documentation](dynamic_forms/documentation.md) (Angular 2 library)
