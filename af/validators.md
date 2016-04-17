### Validators
Validators are used when you want to add some frontend validation to your forms and thus make form richer.

##### RegExpValidator
```java
RegExpValidator(String message, String value)
```
Tests if input text is member of Regular language which is defined by given regular expression.
* `message` is displayed when validation fails
* `value` is regular expression which validator is testing, keep in mind this is for javascript

##### RequiredValidator
```java
RequiredValidator(String message, Boolean value)
```
Fails when input is submitted empty.

##### MinStringLengthValidator
```java
MinStringLengthValidator(String message, Integer value)
```
Checks if input text has more chars than given value.
* `value` is number of minimum string length of input

##### MaxStringLengthValidator
```java
MaxStringLengthValidator(String message, Integer value)
```
Checks if input text has less chars than given value.
* `value` is number of maximum string length of input

##### MinNumberValidator
```java
MinNumberValidator(String message, Integer value)
```
Validates if submitted number is higher than given value.
* `value` is minimal number which can be submitted

##### MaxNumberValidator
```java
MaxNumberValidator(String message, Integer value)
```
Validates if submitted number is lower than given value.
* `value` is maximal number which can be submitted

###### EmailValidator
```java
EmailValidator(String message)
```
Fails when invalid email address is submitted.

##### DateUntilValidator
```java
DateUntilValidator(String message, Date value)
```
Fails when date after provided value is submitted.

##### DateSinceValidator
```java
DateSinceValidator(String message, Date value)
```
Fails when date before provided value is submitted.
