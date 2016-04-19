### DynamicForms
###### Angular 2 library
Make sure `DynamicFormComponent` is properly imported.
```html
<dynamic-form [structure]="formStructure" [optionsFnc]="_form.getOptions" (submit)="onSubmit($event)"></dynamic-form>
```
* `formStructure` is json obtained from server containing form structure
* `_form.getOptions` is function returning `Observable<Response>` and it must handle the call for options, see example in demo [here](../demo/src/main/webapp/ng2af/app/form.service.ts)
* `submit($event)` is function which is fired when submit button is fired (and form is valid), output values can be obtained from function argument, see [demo](../demo/src/main/webapp/ng2af/app/book.component.ts) for usage example.

##### Styling
In default, this library using [Pure.css](http://purecss.io/). You can override default html classes attributes through backend app.

Pseudo-html of rendered form
```html
<form class="htmlClassOfForm">
  <div class="htmlClassOfInputsSurroundingDiv">
    <label>..</label>
    <input id="name">
    <div class="htmlClassOfInputsErrorMessageDiv"> error message text </div>
  </div>
  .
  .
  .
  <div class="htmlClassOfButtonSurroundingDiv">
    <button class="htmlClassOfSubmitButton">Submit</button>
  </div>
  <div class="htmlClassOfFormErrorMessageDiv"> error message text </div>
</form>
```

Override class attributes or use [default](../af/inputs.md) class names. You can utilize id attribute of each input element which is set to name parameter of input.

DynamicForms uses standard Angular 2 input state classes:

| State                     | Class if *true* | Class if *false* |
| ------------------------- |:-------------:|:--------------:|
| Input has been visited    | ng-touched    | ng-untouched   |
| Input's value has changed | ng-dirty      | ng-pristine    |
| Input's value is valid    | ng-valid      | ng-invalid     |
