### ReactiveDynamicForms
Written in ES5 javascript. ReactiveDynamicForms is a component for [ReactJS](http://facebook.github.io/react/) library.

#### Usage
```js
import DynamicForm from 'reactiveAf';
import React from 'react';

export default SimpleComponent extends React.Component {

   ...

   render() {
     return(
       <DynamicForm
          structure={this.state.formStructure}
          onSubmit={this.onSubmit.bind(this)}
          getOptions={this.getOptions}
       />
     );
   }
}
```

Where `structure` is JSON with form structure obtained from backend, the `onSubmit` parameter is callback function which is executed on valid form submit and the `getOptions` is also a callback function which is used for making a request for additional form options (must return [when](https://github.com/cujojs/when/) promise).

See [main documentation ](../README.md) for more informations.

#### Styling
Styling is same as [here](../dynamic_forms/documentation.md).
