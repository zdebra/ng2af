### Option
Options are used as Data Transfer Objects, which transfer minimal required amount of data for html input options.

##### StringOption
```java
StringOption(String label, String name, String value)
```
* `label` is what is displayed to the user
* `name` is name attribute of the option, is responded when `ResponseType.NAME` is selected
* `value` is value attribute of the option, is responded when `ResponseType.VALUE` is selected

##### NumberOption
```java
NumberOption(String label, String name, Integer value)
```

##### EntityOption
```java
EntityOption(String label, String name, Long entityId)
```
* `entityId` represents entity

##### BooleanOption
```java
BooleanOption(String label, String name, Boolean value)
```
