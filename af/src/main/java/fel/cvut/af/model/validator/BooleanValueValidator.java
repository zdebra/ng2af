package fel.cvut.af.model.validator;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import fel.cvut.af.util.serializer.BooleanValueValidatorSerializer;

import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

/**
 * An abstract predecessor for validators with boolean type of value.
 * <p>
 * @see Validator
 */
@MappedSuperclass
@JsonSerialize(using = BooleanValueValidatorSerializer.class)
public abstract class BooleanValueValidator extends Validator<Boolean> {

    /**
     * The value is passed to the validator
     */
    @NotNull
    private Boolean value;

    /**
     *
     * @param message is displayed when validator fails
     * @param value is passed to the validator
     */
    public BooleanValueValidator(String message, Boolean value) {
        super(message);
        this.value = value;
    }

    public BooleanValueValidator() {
        super();
    }

    @Override
    public Boolean getValue() {
        return this.value;
    }

    @Override
    public void setValue(Boolean value) {
        this.value = value;
    }
}
