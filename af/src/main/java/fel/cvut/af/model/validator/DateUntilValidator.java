package fel.cvut.af.model.validator;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;
import java.util.Date;

/**
 * An entity class for date validation.
 * <p>
 * This validator fails when user apply date which is after provided date value.
 * <p>
 * @see DateValueValidator
 */
@Entity
@DiscriminatorValue("DATE_UNTIL")
public class DateUntilValidator extends DateValueValidator {

    /**
     * The constant, name of validator.
     */
    @Transient
    private static final String NAME = "dateUntil";

    /**
     *
     * @param message is displayed when validator fails
     * @param value validator fails when date after this value is submitted
     */
    public DateUntilValidator(String message, Date value) {
        super(message, value);
    }

    public DateUntilValidator() {
    }

    @Override
    public String getName() {
        return NAME;
    }
}
