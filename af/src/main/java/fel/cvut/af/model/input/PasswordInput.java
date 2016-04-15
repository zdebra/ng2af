package fel.cvut.af.model.input;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;
import java.util.Collection;

/**
 * An entity class for password input.
 * <p>
 * This class is system representation for what should be rendered as a input with password as type attribute.
 */
@Entity
@DiscriminatorValue("PASSWORD")
public class PasswordInput extends Input {

    /**
     * Constant which is used as a type of input and appears as an attribute of JSON serialized object.
     */
    @Transient
    private final String inputType = "PASSWORD";

    public PasswordInput() {
        super();
    }

    /**
     *
     * @param name the name attribute
     * @param placeholder the placeholder attribute
     * @param label the inside of label tag
     * @param roles the roles who can see this input
     */
    public PasswordInput(String name, String placeholder, String label, Collection<String> roles) {
        super(name, placeholder, label, roles, ResponseFormat.VALUE);
    }

    @Override
    public String getInputType() {
        return inputType;
    }
}
