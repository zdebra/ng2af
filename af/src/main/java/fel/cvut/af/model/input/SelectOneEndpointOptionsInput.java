package fel.cvut.af.model.input;

import fel.cvut.af.model.options.Option;
import fel.cvut.af.util.serializer.DefaultValueSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;
import java.util.Collection;

/**
 * An entity class for Selectbox where only one value can be selected.
 * <p>
 * The options can represent different value types, see {@link Option} and his descendants.
 * The options are provided as a url leading to REST endpoint where options can be requested from.
 * <p>
 * Use this class when {@code Options} has bigger size or you want to have those in different request.
 * There is also an implementation of SelectOne where {@code Options} can be supplied directly as a persisted list.
 * See {@link SelectOneBasicOptionsInput}.
 */
@Entity
@DiscriminatorValue("SELECT_ONE_ENDPOINT")
public class SelectOneEndpointOptionsInput extends EndpointOptionsInput {

    /**
     * Constant which is used as a type of input and appears as an attribute of JSON serialized object.
     */
    @Transient
    private static final String inputType = "SELECT_ONE";

    /**
     * The preselected value
     */
    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name="option_id")
    @JsonSerialize(using = DefaultValueSerializer.class)
    private Option defaultValue;

    public SelectOneEndpointOptionsInput() {
        super();
    }

    /**
     *
     * @param name the name attribute
     * @param placeholder the placeholder attribute
     * @param label the inside of label tag
     * @param roles the roles who can see this input
     * @param endpoint the url where the options can be requested from
     * @param responseFormat the expected format of response
     * @param defaultValue the preselected value, should be member of options parameter
     */
    public SelectOneEndpointOptionsInput(String name, String placeholder, String label,
                                         Collection<String> roles, String endpoint, Option defaultValue,
                                         ResponseFormat responseFormat) {
        super(name, placeholder, label, roles, endpoint, responseFormat);
        this.defaultValue = defaultValue;
    }

    /**
     *
     * @param name the name attribute
     * @param placeholder the placeholder attribute
     * @param label the inside of label tag
     * @param roles the roles who can see this input
     * @param endpoint the url where the options can be requested from
     * @param responseFormat the expected format of response
     */
    public SelectOneEndpointOptionsInput(String name, String placeholder, String label, Collection<String> roles,
                                         String endpoint, ResponseFormat responseFormat) {
        super(name, placeholder, label, roles, endpoint, responseFormat);
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
     * @param defaultValue the preselected value, can be null, should be member of {@code options}
     */
    public void setDefaultValue(Option defaultValue) {
        this.defaultValue = defaultValue;
    }

    @Override
    public String getInputType() {
        return inputType;
    }
}
