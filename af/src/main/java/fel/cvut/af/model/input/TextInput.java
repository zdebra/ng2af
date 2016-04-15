package fel.cvut.af.model.input;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;
import java.util.Collection;

/**
 * An entity class for Text input.
 * <p>
 * This is system representation for input with string value.
 * It is designed for shorter inputs than {@link TextFieldInput}.
 * <p>
 * It should be rendered as input element with type attribute set to text.
 */
@Entity
@DiscriminatorValue("TEXT")
public class TextInput extends Input {

    /**
     * Constant which is used as a type of input and appears as an attribute of JSON serialized object.
     */
    @Transient
    private final String inputType = "TEXT";

    /**
     * The prefilled value.
     */
    private String defaultValue = "";

    /**
     *
     * @param name the name attribute
     * @param placeholder the placeholder attribute
     * @param label the inside of label tag
     * @param roles the roles who can see this input
     * @param defaultValue the prefilled value
     */

    public TextInput(String name, String placeholder, String label, Collection<String> roles,
                     String defaultValue) {
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
    public TextInput(String name, String placeholder, String label, Collection<String> roles) {
        super(name, placeholder, label, roles, ResponseFormat.VALUE);
    }

    public TextInput() {
        super();
    }

    /**
     *
     * @return the prefilled value
     */
    public String getDefaultValue() {
        return defaultValue;
    }

    /**
     *
     * @param defaultValue the prefilled value, can be empty
     */
    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    @Override
    public String getInputType() {
        return inputType;
    }
}
