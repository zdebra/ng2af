package fel.cvut.af.model.validator;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import fel.cvut.af.util.serializer.IntegerValueValidatorSerializer;

import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

/**
 * An abstract predecessor for validators with Integer value.
 * <p>
 * @see Validator
 */
@MappedSuperclass
@JsonSerialize(using = IntegerValueValidatorSerializer.class)
public abstract class IntegerValueValidator extends Validator<Integer> {

    /**
     * The value passed to the validator.
     */
    @NotNull
    private Integer value;

    /**
     *
     * @param message is displayed when validation fails
     * @param value is passed to the validator
     */
    public IntegerValueValidator(String message, Integer value) {
        super(message);
        this.value = value;
    }

    public IntegerValueValidator() {
        super();
    }

    @Override
    public Integer getValue() {
        return this.value;
    }

    @Override
    public void setValue(Integer value) {
        this.value = value;
    }
}
