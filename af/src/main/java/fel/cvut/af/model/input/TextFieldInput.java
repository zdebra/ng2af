package fel.cvut.af.model.input;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;
import java.util.Collection;

/**
 * An entity class for TextField input.
 * <p>
 * This is system representation for input with string value.
 * It is designed for longer inputs than {@link TextInput}.
 * <p>
 * It should be rendered as textfield element.
 */
@Entity
@DiscriminatorValue("TEXTFIELD")
public class TextFieldInput extends Input {

    /**
     * Constant which is used as a type of input and appears as an attribute of JSON serialized object.
     */
    @Transient
    private final String inputType = "TEXTFIELD";

    /**
     * The prefilled value.
     */
    private String defaultValue = "";

    public TextFieldInput() {
        super();
    }

    /**
     *
     * @param name the name attribute
     * @param placeholder the placeholder attribute
     * @param label the inside of label tag
     * @param roles the roles who can see this input
     * @param defaultValue the prefilled value
     */
    public TextFieldInput(String name, String placeholder, String label, Collection<String> roles, String defaultValue) {
        super(name, placeholder, label, roles,ResponseFormat.VALUE);
        this.defaultValue = defaultValue;
    }

    /**
     *
     * @param name the name attribute
     * @param placeholder the placeholder attribute
     * @param label the inside of label tag
     * @param roles the roles who can see this input
     */
    public TextFieldInput(String name, String placeholder, String label, Collection<String> roles) {
        super(name, placeholder, label, roles,ResponseFormat.VALUE);
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
