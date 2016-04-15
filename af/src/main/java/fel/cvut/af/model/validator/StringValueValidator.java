package fel.cvut.af.model.validator;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import fel.cvut.af.util.serializer.StringValueValidatorSerializer;

import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

/**
 * An abstract predecessor for validators with string type value
 *
 * @see Validator
 */
@MappedSuperclass
@JsonSerialize(using = StringValueValidatorSerializer.class)
public abstract class StringValueValidator extends Validator<String> {

    /**
     * The validator parameter.
     */
    @NotNull
    private String value;

    /**
     *
     * @param message is displayed when validation fails
     * @param value is passed to validator
     */
    public StringValueValidator(String message, String value) {
        super(message);
        this.value = value;
    }

    public StringValueValidator() {
        super();
    }

    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public void setValue(String value) {
        this.value = value;
    }
}
