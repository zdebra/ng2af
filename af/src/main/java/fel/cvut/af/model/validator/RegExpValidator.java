package fel.cvut.af.model.validator;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

/**
 * An entity class for Regular expression validator.
 * <p>
 * This validator fails when input isn't member of regular language represented by given regular expression.
 * <p>
 * @see StringValueValidator
 */
@Entity
@DiscriminatorValue("REGEXP")
public class RegExpValidator extends StringValueValidator {

    /**
     * The constant, name of validator.
     */
    @Transient
    private static final String NAME = "regExp";

    /**
     *
     * @param message is displayed when validation fails
     * @param value is regular expression, it should be valid for javascript
     */
    public RegExpValidator(String message, String value) {
        super(message, value);
    }

    public RegExpValidator() {
    }

    @Override
    public String getName() {
        return NAME;
    }
}
