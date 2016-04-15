package fel.cvut.af.model.options;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

/**
 * An Entity class for options where elements are JPA entities.
 * <p>
 * @see Option
 */
@Entity
@DiscriminatorValue("NUMBER")
public class NumberOption extends Option<Integer> {

    /**
     * The numeric value.
     */
    @NotNull
    private Integer value;

    public NumberOption() {
    }

    /**
     *
     * @param label in human readable form
     * @param name should be unique in scope of input
     * @param value transferred numeric value
     */
    public NumberOption(String label, String name, Integer value) {
        super(label, name);
        this.value = value;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    @Override
    public void setValue(Integer value) {
        this.value = value;
    }
}
