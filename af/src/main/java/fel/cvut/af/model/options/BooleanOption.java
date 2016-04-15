package fel.cvut.af.model.options;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

/**
 * An Entity class for boolean value option.
 * <p>
 * @see Option
 */
@Entity
@DiscriminatorValue("BOOLEAN")
public class BooleanOption extends Option<Boolean> {

    /**
     * The value of option.
     */
    @NotNull
    private Boolean value;

    public BooleanOption() {
        super();
    }

    /**
     *
     * @param label in human readable form
     * @param name should be unique in scope of input
     * @param value transferred value
     */
    public BooleanOption(String label, String name, Boolean value) {
        super(label, name);
        this.value = value;
    }

    @Override
    public Boolean getValue() {
        return value;
    }

    @Override
    public void setValue(Boolean value) {
        this.value = value;
    }
}
