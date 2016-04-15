package fel.cvut.af.model.validator;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

/**
 * An entity class for maximum length of string validator.
 * <p>
 * This validator fails when longer string than given value is applied.
 * <p>
 * @see IntegerValueValidator
 */
@Entity
@DiscriminatorValue("MAX_STRING_LENGTH")
public class MaxStringLengthValidator extends IntegerValueValidator {

    /**
     * The constant, name of validator.
     */
    @Transient
    private static final String NAME = "strLenMax";

    /**
     *
     * @param message is displayed when validation fails
     * @param value is a maximal length of input string
     */
    public MaxStringLengthValidator(String message, Integer value) {
        super(message, value);
    }

    public MaxStringLengthValidator() {
    }

    @Override
    public String getName() {
        return NAME;
    }
}