### Input
##### TextInput
```java
TextInput(String name, String placeholder, String label, Collection<String> roles, String defaultValue)
```

* `name` is used as a html input attribute name, must be unique in scope of form
* `placeholder` is used as a html input placeholder attribute
* `label` is used inside label html tag, user will see this right next to input field
* `roles` is collection of roles for who is this input visible and accessible, you will recall this while building a form
* `defaultValue` is what is prefilled in this input field

##### TextFieldInput
```java
TextFieldInput(String name, String placeholder, String label, Collection<String> roles, String defaultValue)
```
The difference between `TextInput` and `TextFieldInput` is that TextInput is represented as a `<input type="text">` and the TextFieldInput by `<textarea></textarea>`. Naturally, TextFieldInput should be used for longer and TextInput for shorter text inputs.

##### NumberInput
```java
NumberInput(String name, String placeholder, String label, Collection<String> roles, Integer defaultValue)
```
`NumberInput` is represented by `<input type="number">`.

##### DateInput
```java
DateInput(String name, String placeholder, String label, Collection<String> roles, Date defaultValue)
```
`DateInput` is represented by `<input type="date">`.

##### PasswordInput
```java
PasswordInput(String name, String placeholder, String label, Collection<String> roles)
```
`PasswordInput` is represented by `<input type="password">`.

##### CheckboxGroupInput
```java
CheckboxGroupInput(String name, String placeholder, String label, Collection<String> roles, Collection<Option> options, ResponseFormat responseFormat, Collection<Option> defaultValue)
```
`CheckboxGroupInput` is rendered as a list of `<input type="checkbox">`. Multiple options can be selected.

* `options` is collection of `Option` from which can be selected, see [options documentation](/options.md).
* `responseFormat` is format of expected response, could be:
  - `ResponseFormat.VALUE` value from Option object is responded
  - `ResponseFormat.NAME` name from Option object is responded
  - `ResponseFormat.OPTION` whole Option object is responded
  - `ResponseFormat.VALUES` array of selected values is responded
  - `ResponseFormat.NAMES` array of selected names is responded
  - `ResponseFormat.OPTIONS` array of selected Option objects is responded

##### RadioButtonGroupInput
```java
RadioButtonGroupInput(String name, String placeholder, String label, Collection<String> roles, Collection<Option> options, ResponseFormat responseFormat, Option defaultValue)
```
`RadioButtonGroupInput` is represented by list of `<input type="radio">`. Single option can be seleted.

##### SelectManyBasicOptionsInput
```java
SelectManyBasicOptionsInput(String name, String placeholder, String label, Collection<String> roles, Collection<Option> options, ResponseFormat responseFormat)
```
`SelectManyBasicOptionsInput` is represented by `<select multiple><option>o1</option> ... </select>`. Multiple options can be selected.

##### SelectManyEndpointOptionsInput
```java
SelectManyEndpointOptionsInput(String name, String placeholder, String label, Collection<String> roles, String endpoint, ResponseFormat responseFormat, Collection<Option> defaultValue)
```
`SelectManyEndpointOptionsInput` is same as his counter part `SelectManyBasicOptionsInput` html selectbox where multiple options can be selected. Difference is in how list of options is provided. In this case is specified `enpoint`, which is url leading to REST endpoint where this list of options can be requested to.
* `enpoint` is url leading to REST endpoint where list of options can be requested

##### SelectOneBasicOptionsInput
```java
SelectOneBasicOptionsInput(String name, String placeholder, String label, Collection<String> roles, Collection<Option> options, ResponseFormat responseFormat)
```
`SelectOneBasicOptionsInput` is represented by `<select><option>o1</option> ... </select>`. Single option can be selected.

##### SelectOneEndpointOptionsInput
```java
SelectOneEndpointOptionsInput(String name, String placeholder, String label,Collection<String> roles, String endpoint, Option defaultValue, ResponseFormat responseFormat)
```
Similarly like SelectMany, this is version of selectbox where only single option can be selected and options are requested in separate http request.
