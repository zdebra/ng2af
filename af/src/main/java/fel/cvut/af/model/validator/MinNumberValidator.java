package fel.cvut.af.model.validator;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

/**
 * An entity class for minimal value of number field.
 * <p>
 * This validator fails when number lower than given value is applied.
 * <p>
 * @see IntegerValueValidator
 */
@Entity
@DiscriminatorValue("MIN_NUMBER")
public class MinNumberValidator extends IntegerValueValidator {

    /**
     * The constant, name of validator.
     */
    @Transient
    private static final String NAME = "numberMin";

    /**
     *
     * @param message is displayed when validation fails
     * @param value is minimum number input field can be
     */
    public MinNumberValidator(String message, Integer value) {
        super(message, value);
    }

    public MinNumberValidator() {
    }

    @Override
    public String getName() {
        return NAME;
    }
}
