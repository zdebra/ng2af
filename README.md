# Angular 2 and ReactJS AspectFaces implementation
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
1. Clone this repository `git clone https://github.com/zdebra/ng2af.git`

2. Get all frontend dependencies. There are two Angular 2 apps and two ReactJS apps in `demo/src/main/webapp` folder:
    - `ng2` is prototype app without usage of `DynamicForms` library
    - `ng2af` is prototype app created on top of `DynamicForms` library
    - `react` is prototype app without usage of `ReactiveDynamicForms` library
    - `react_af` is prototype app created on top of `ReactiveDynamicForms` library

        Run `npm link ../../../../../dynamic_forms` to link local npm package inside `ng2af` subdirectory. You might use sudo for this command, depends on your npm settings. Then navigate to both `ng2` and `ng2af` subdirectories and run `npm install` here.

        The same process goes for React prototypes. For `react_af` link library with command `npm link ../../../../../reactive_dynamic_forms` and then in both directories `react_af` and `react` run `npm install`.

        This should install all required frontend dependencies.

3. Angular 2 is developed in Typescript, which nowday's browsers can't process. So you need to transpile all `.ts` files into the javascript. There are two ways how to achieve this:
  - use your built-in transpiler of your IDE (recomended)
  - run `tsc` in command line, you probably need to install typescript globally first (`npm install typescript -g`), you might get bunch of warnings but important is whether it generates `.js` files inside the `/app` directory.

    You must transpile source codes in both `ng2` and `ng2af` prototypes (or in those which you want to use).

4. React prototypes are written in ES2015 (also known as ES6). You must transpile source codes, similarly like in previous step for Angular 2. Run `npm run build-js` in both React prototypes directories. This will compile everything into the `build/build.js` file.

5. You need application server for Java EE7. I am using [Wildfly 10.0.0.Final](http://wildfly.org/downloads/).

6. Create deployable package. Navigate to the root directory of demo and run `mvn package`. It creates the archive as well as obtains all required server dependencies.

7. Deploy `war` archive.

### Documentation
* [Input documentation](af/inputs.md)
* [Option documentation](af/options.md)
* [Validators documentation](af/validators.md)
* [DynamicForms documentation](dynamic_forms/documentation.md) (Angular 2 library)
* [ReactiveDynamicForms documentation](reactive_dynamic_forms/documentation.md) (ReactJS library)
