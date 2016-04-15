package fel.cvut.af.model.input;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import fel.cvut.af.model.options.Option;
import fel.cvut.af.util.serializer.DefaultValueCollectionSerializer;

import javax.persistence.*;
import java.util.Collection;

/**
 * An Entity class for Checkbox input.
 * <p>
 * This entity is system representation of what is JSON serialized and then it should be rendered as a group of
 * checkbox inputs.
 * It offers option to represent input value in different types, see {@link Option} descendants.
 * It is meant to be input where multiple values can be selected, as an opposite to {@link RadioButtonGroupInput}.
 */
@Entity
@DiscriminatorValue("CHECKBOX_GROUP")
public class CheckboxGroupInput extends BasicOptionsInput {

    /**
     * Constant which is used as a type of input and appears as an attribute of JSON serialized object.
     */
    @Transient
    private static final String inputType = "CHECKBOX_GROUP";

    /**
     * Collection of {@code Option} used as default values.
     * Those should be rendered as a preselected options.
     */
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "inputDefault")
    @JsonSerialize(using = DefaultValueCollectionSerializer.class)
    private Collection<Option> defaultValue;

    public CheckboxGroupInput() {
        super();
    }

    /**
     *
     * @param name the name attribute
     * @param placeholder the placeholder attribute
     * @param label the inside of label tag
     * @param roles the roles who can see this input
     * @param options the options which will be chosen from
     * @param responseFormat the expected format of response
     * @param defaultValue the preselected options, should be members of parameter options as well
     */
    public CheckboxGroupInput(String name, String placeholder, String label, Collection<String> roles, Collection<Option> options, ResponseFormat responseFormat, Collection<Option> defaultValue) {
        super(name, placeholder, label, roles, options, responseFormat);
        this.defaultValue = defaultValue;
    }

    /**
     *
     * @param name the name attribute
     * @param placeholder the placeholder attribute
     * @param label the inside of label tag
     * @param roles the roles who can see this input
     * @param options the options which will be chosen from
     * @param responseFormat the expected format of response
     */
    public CheckboxGroupInput(String name, String placeholder, String label, Collection<String> roles, Collection<Option> options, ResponseFormat responseFormat) {
        super(name, placeholder, label, roles, options, responseFormat);
    }

    /**
     * Simple getter.
     *
     * @return the default value of this input, could be null
     */
    public Collection<Option> getDefaultValue() {
        return defaultValue;
    }

    /**
     * Simple setter.
     *
     * @param defaultValue the default value of this input, could be null
     */
    public void setDefaultValue(Collection<Option> defaultValue) {
        this.defaultValue = defaultValue;
    }

    @Override
    public String getInputType() {
        return inputType;
    }
}
