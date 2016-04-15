package fel.cvut.af.model.input;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import fel.cvut.af.model.options.Option;
import fel.cvut.af.util.serializer.DefaultValueSerializer;

import javax.persistence.*;
import java.util.Collection;

/**
 * An entity class for Radiobutton group of inputs.
 * <p>
 * Radiobutton is input type of input where user can chose one of available options.
 * See the opposite {@link CheckboxGroupInput}.
 * <p>
 * The options can represent different value types, see {@link Option} and his descendants.
 */
@Entity
@DiscriminatorValue("RADIOBUTTON")
public class RadioButtonGroupInput extends BasicOptionsInput {

    /**
     * Constant which is used as a type of input and appears as an attribute of JSON serialized object.
     */
    @Transient
    private static final String inputType = "RADIOBUTTON_GROUP";

    /**
     * The preselected option.
     * Should be member of input {@code options}.
     */
    @OneToOne
    @JoinColumn(name="option_id")
    @JsonSerialize(using = DefaultValueSerializer.class)
    private Option defaultValue;

    public RadioButtonGroupInput() {
        super();
    }

    /**
     *
     * @param name the name attribute
     * @param placeholder the placeholder attribute
     * @param label the inside of label tag
     * @param roles the roles who can see this input
     * @param options the options from user can select
     * @param responseFormat the expected format of response
     * @param defaultValue the preselected value, should be member of options parameter
     */
    public RadioButtonGroupInput(String name, String placeholder, String label, Collection<String> roles, Collection<Option> options, ResponseFormat responseFormat, Option defaultValue) {
        super(name, placeholder, label, roles, options, responseFormat);
        this.defaultValue = defaultValue;
    }

    /**
     *
     * @param name the name attribute
     * @param placeholder the placeholder attribute
     * @param label the inside of label tag
     * @param roles the roles who can see this input
     * @param options the options from user can select
     * @param responseFormat the expected format of response
     */
    public RadioButtonGroupInput(String name, String placeholder, String label, Collection<String> roles, Collection<Option> options, ResponseFormat responseFormat) {
        super(name, placeholder, label, roles, options, responseFormat);
    }


    /**
     *
     * @return the preselected option, could be null
     */
    public Option getDefaultValue() {
        return defaultValue;
    }

    /**
     *
     * @param defaultValue the preselected option, should be member of {@code options}
     */
    public void setDefaultValue(Option defaultValue) {
        this.defaultValue = defaultValue;
    }

    @Override
    public String getInputType() {
        return inputType;
    }
}
