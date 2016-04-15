package fel.cvut.af.model.validator;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

/**
 * An entity class for minimum length of string value.
 * <p>
 * This validator fails when input shorter than given value is applied.
 * <p>
 * @see IntegerValueValidator
 */
@Entity
@DiscriminatorValue("MIN_STRING_LENGTH")
public class MinStringLengthValidator extends IntegerValueValidator {

    /**
     * The constant, name of validator.
     */
    @Transient
    private static final String NAME = "strLenMin";

    /**
     *
     * @param message is displayed when validation fails
     * @param value is a minimal count of characters that string value has to have
     */
    public MinStringLengthValidator(String message, Integer value) {
        super(message, value);
    }

    public MinStringLengthValidator() {
    }

    @Override
    public String getName() {
        return NAME;
    }
}