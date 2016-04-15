package fel.cvut.af.model.validator;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

/**
 * An entity class for required validator.
 * <p>
 * This validator fails when input is empty.
 * <p>
 * @see BooleanValueValidator
 */
@Entity
@DiscriminatorValue("REQUIRED")
public class RequiredValidator extends BooleanValueValidator {

    /**
     * The constant, name of validator.
     */
    @Transient
    private static final String NAME = "required";

    /**
     *
     * @param message is displayed when validation fails
     * @param value is passed to validator
     */
    public RequiredValidator(String message, Boolean value) {
        super(message,value);
    }

    public RequiredValidator() {}

    @Override
    public String getName() {
        return NAME;
    }
}
