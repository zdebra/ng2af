package fel.cvut.af.model.options;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

/**
 * An Entity class for string value options.
 * <p>
 * @see Option
 */
@Entity
@DiscriminatorValue("STRING")
public class StringOption extends Option<String> {

    /**
     * The transferred value.
     */
    @NotNull
    @NotEmpty
    private String strValue;

    public StringOption() {
        super();
    }

    /**
     *
     * @param label in human readable form
     * @param name should be unique in scope of input
     * @param value transferred string value
     */
    public StringOption(String label, String name, String value) {
        super(label, name);
        this.strValue = value;
    }

    @Override
    public String getValue() {
        return strValue;
    }

    @Override
    public void setValue(String value) {
        this.strValue = value;
    }
}
