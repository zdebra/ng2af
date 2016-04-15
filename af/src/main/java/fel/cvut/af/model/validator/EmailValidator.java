package fel.cvut.af.model.validator;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

/**
 * An entity class for validating email address.
 * <p>
 * This validator fails when invalid email address is applied.
 * <p>
 * @see BooleanValueValidator
 */
@Entity
@DiscriminatorValue("EMAIL")
public class EmailValidator extends BooleanValueValidator {

    /**
     * The constant, name of the validator.
     */
    @Transient
    private static final String NAME = "email";

    /**
     *
     * @param message is displayed when validation fails
     */
    public EmailValidator(String message) {
        super(message, true);
    }

    public EmailValidator() {
    }

    @Override
    public String getName() {
        return this.NAME;
    }
}
