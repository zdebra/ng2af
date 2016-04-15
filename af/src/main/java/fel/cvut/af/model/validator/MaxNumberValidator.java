package fel.cvut.af.model.validator;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

/**
 * An entity class for maximum number validator.
 * <p>
 * This validator fails when higher number than given value is applied.
 * <p>
 * @see IntegerValueValidator
 */
@Entity
@DiscriminatorValue("MAX_NUMBER")
public class MaxNumberValidator extends IntegerValueValidator {

    /**
     * The constant, name of validator.
     */
    @Transient
    private static final String NAME = "numberMax";

    /**
     *
     * @param message is displayed when validation fails
     * @param value is maximum number value user can pass
     */
    public MaxNumberValidator(String message, Integer value) {
        super(message, value);
    }

    public MaxNumberValidator() {
    }

    @Override
    public String getName() {
        return NAME;
    }
}
