package fel.cvut.af.model.input;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import fel.cvut.af.model.options.Option;
import fel.cvut.af.util.serializer.DefaultValueSerializer;

import javax.persistence.*;
import java.util.Collection;

/**
 * An entity class for Selectbox where only one value can be selected.
 * <p>
 * The options are provided directly as a persisted list of {@link Option}.
 * The options can represent different value types, see {@link Option} and his descendants.
 * <p>
 * Use this class when {@code Options} hasn't too many elements.
 * There is also an implementation of SelectOne where {@code Options} can be defined as a url leading to REST endpoint.
 * See {@link SelectOneEndpointOptionsInput}.
 */
@Entity
@DiscriminatorValue("SELECT_ONE_BASIC")
public class SelectOneBasicOptionsInput extends BasicOptionsInput {

    /**
     * Constant which is used as a type of input and appears as an attribute of JSON serialized object.
     */
    @Transient
    private static final String inputType = "SELECT_ONE";

    /**
     * The preselected value.
     */
    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name="option_id")
    @JsonSerialize(using = DefaultValueSerializer.class)
    private Option defaultValue;

    public SelectOneBasicOptionsInput() {
        super();
    }

    /**
     *
     * @param name the name attribute
     * @param placeholder the placeholder attribute
     * @param label the inside of label tag
     * @param roles the roles who can see this input
     * @param options the options from user can select
     * @param responseFormat the expected format of response
     * @param defaultValue the preselected value, should be member of options parameter
     */
    public SelectOneBasicOptionsInput(String name, String placeholder, String label,
                                      Collection<String> roles, Collection<Option> options, ResponseFormat responseFormat,
                                      Option defaultValue) {
        super(name, placeholder, label, roles, options,responseFormat);
        this.defaultValue = defaultValue;
    }

    /**
     *
     * @param name the name attribute
     * @param placeholder the placeholder attribute
     * @param label the inside of label tag
     * @param roles the roles who can see this input
     * @param options the options from user can select
     * @param responseFormat the expected format of response
     */
    public SelectOneBasicOptionsInput(String name, String placeholder, String label, Collection<String> roles,
                                      Collection<Option> options, ResponseFormat responseFormat) {
        super(name, placeholder, label, roles, options, responseFormat);
    }

    /**
     *
     * @return the preselected value
     */
    public Option getDefaultValue() {
        return defaultValue;
    }

    /**
     *
     * @param defaultValue the preselected value, should be member of {@code Options}, can be null
     */
    public void setDefaultValue(Option defaultValue) {
        this.defaultValue = defaultValue;
    }

    @Override
    public String getInputType() {
        return inputType;
    }

}
