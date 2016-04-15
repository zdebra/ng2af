package fel.cvut.af.model.validator;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import fel.cvut.af.util.serializer.DateValueValidatorSerializer;

import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * An abstract predecessor for date value validators.
 * <p>
 * @see DateValueValidator
 */
@MappedSuperclass
@JsonSerialize(using = DateValueValidatorSerializer.class)
public abstract class DateValueValidator extends Validator<Date> {

    /**
     * The value is passed to the validator.
     */
    @NotNull
    private Date value;

    /**
     *
     * @param message is displayed when validation fails
     * @param value is passed to the validator
     */
    public DateValueValidator(String message, Date value) {
        super(message);
        this.value = value;
    }

    public DateValueValidator() {
        super();
    }

    @Override
    public Date getValue() {
        return this.value;
    }

    @Override
    public void setValue(Date value) {
        this.value = value;
    }
}
