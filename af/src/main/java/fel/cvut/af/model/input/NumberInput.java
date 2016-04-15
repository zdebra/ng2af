package fel.cvut.af.model.input;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;
import java.util.Collection;

/**
 * An Entity class for number input.
 * <p>
 * This entity is system representation of input which have numeric type of value.
 */
@Entity
@DiscriminatorValue("NUMBER")
public class NumberInput extends Input {

    /**
     * Constant which is used as a type of input and appears as an attribute of JSON serialized object.
     */
    @Transient
    private final String inputType = "NUMBER";

    /**
     * The prefilled value of input field.
     */
    private Integer defaultValue;

    public NumberInput() {
        super();
    }

    /**
     *
     * @param name the name attribute
     * @param placeholder the placeholder attribute
     * @param label the inside of label tag
     * @param roles the roles who can see this input
     * @param defaultValue the prefilled value of input field, could be null
     */
    public NumberInput(String name, String placeholder, String label, Collection<String> roles, Integer defaultValue) {
        super(name, placeholder, label, roles, ResponseFormat.VALUE);
        this.defaultValue = defaultValue;
    }

    /**
     *
     * @param name the name attribute
     * @param placeholder the placeholder attribute
     * @param label the inside of label tag
     * @param roles the roles who can see this input
     */
    public NumberInput(String name, String placeholder, String label, Collection<String> roles) {
        super(name, placeholder, label, roles, ResponseFormat.VALUE);
    }

    /**
     *
     * @return the prefilled value of field
     */
    public Integer getDefaultValue() {
        return defaultValue;
    }

    /**
     *
     * @param defaultValue the prefilled value of input field, could be null
     */
    public void setDefaultValue(Integer defaultValue) {
        this.defaultValue = defaultValue;
    }

    @Override
    public String getInputType() {
        return inputType;
    }
}
