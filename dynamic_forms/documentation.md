### DynamicForms
###### Angular 2 library
Make sure `DynamicFormComponent` is properly imported.
```html
<dynamic-form [structure]="formStructure" [optionsFnc]="_form.getOptions" (submit)="onSubmit($event)"></dynamic-form>
```
* `formStructure` is json obtained from server containing form structure
* `_form.getOptions` is function returning `Observable<Response>` and it must handle the call for options, see example in demo [here](../demo/src/main/webapp/ng2af/app/form.service.ts)
* `submit($event)` is function which is fired when submit button is fired (and form is valid), output values can be obtained from function argument, see [demo](../demo/src/main/webapp/ng2af/app/book.component.ts) for usage example.
