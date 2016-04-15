package fel.cvut.af.model.validator;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;
import java.util.Date;

/**
 * An entity class for date validation.
 * <p>
 * This validator fails when user apply date which is before provided date value.
 * <p>
 * @see DateValueValidator
 */
@Entity
@DiscriminatorValue("DATE_SINCE")
public class DateSinceValidator extends DateValueValidator {

    /**
     * The constant, name of validator.
     */
    @Transient
    private static final String NAME = "dateSince";

    /**
     *
     * @param message is displayed when validator fails
     * @param value validator fails when date before this value is submitted
     */
    public DateSinceValidator(String message, Date value) {
        super(message, value);
    }

    public DateSinceValidator() {
    }

    @Override
    public String getName() {
        return NAME;
    }
}
