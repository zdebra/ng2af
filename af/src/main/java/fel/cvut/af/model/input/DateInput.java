package fel.cvut.af.model.input;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import fel.cvut.af.util.serializer.DateSerializer;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

/**
 * An Entity class for dates Input.
 * <p>
 * This entity is system representation for input of dates.
 * It is should be rendered as html input where type attribute is date.
 */
@Entity
@DiscriminatorValue("DATE")
public class DateInput extends Input {

    /**
     * Constant which is used as a type of input and appears as an attribute of JSON serialized object.
     */
    @Transient
    private final String inputType = "DATE";

    /**
     * The value of input.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @JsonSerialize(using = DateSerializer.class)
    private Date defaultValue;

    /**
     * Constructor including defaultValue.
     *
     * @param name the name attribute
     * @param placeholder the placeholder attribute
     * @param label the inside of label tag
     * @param roles the roles who can see this input
     * @param defaultValue the prefilled date
     */
    public DateInput(String name, String placeholder, String label, Collection<String> roles, Date defaultValue) {
        super(name, placeholder, label, roles, ResponseFormat.VALUE);
        this.defaultValue = defaultValue;
    }

    /**
     * Constructor leaving defaultValue unset.
     *
     * @param name the name attribute
     * @param placeholder the placeholder attribute
     * @param label the inside of label tag
     * @param roles the roles who can see this input
     */
    public DateInput(String name, String placeholder, String label, Collection<String> roles) {
        super(name, placeholder, label, roles, ResponseFormat.VALUE);
    }

    public DateInput() {
        super();
    }

    /**
     * Simple getter.
     *
     * @return the prefilled value, could be null
     */
    public Date getDefaultValue() {
        return defaultValue;
    }

    /**
     * Simple setter.
     *
     * @param defaultValue the prefilled value
     */
    public void setDefaultValue(Date defaultValue) {
        this.defaultValue = defaultValue;
    }


    @Override
    public String getInputType() {
        return inputType;
    }
}
