package fel.cvut.af.model.input;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import fel.cvut.af.model.options.Option;
import fel.cvut.af.util.serializer.DefaultValueCollectionSerializer;

import javax.persistence.*;
import java.util.Collection;

/**
 * An entity class for Selectbox allowing multiple options to be selected.
 * <p>
 * The options are supplied as a url to REST endpoint.
 * This is suitable for bigger amount of elements of options or when you want to have those in different http request.
 * There is implementation of {@code SelectMany} where options are provided as a direct, persisted list of {@link Option}.
 * See {@link SelectManyBasicOptionsInput}.
 */
@Entity
@DiscriminatorValue("SELECT_MANY_ENDPOINT")
public class SelectManyEndpointOptionsInput extends EndpointOptionsInput {

    /**
     * Constant which is used as a type of input and appears as an attribute of JSON serialized object.
     */
    @Transient
    private static final String inputType = "SELECT_MANY";

    /**
     * The preselected values.
     */
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "inputDefault")
    @JsonSerialize(using = DefaultValueCollectionSerializer.class)
    private Collection<Option> defaultValue;

    public SelectManyEndpointOptionsInput() {
        super();
    }

    /**
     *
     * @param name the name attribute
     * @param placeholder the placeholder attribute
     * @param label the inside of label tag
     * @param roles the roles who can see this input
     * @param endpoint the url leading to REST endpoint where options can be requested from
     * @param responseFormat the expected format of response
     * @param defaultValue the preselected value, should be member of options
     */
    public SelectManyEndpointOptionsInput(String name, String placeholder, String label, Collection<String> roles, String endpoint, ResponseFormat responseFormat, Collection<Option> defaultValue) {
        super(name, placeholder, label, roles, endpoint, responseFormat);
        this.defaultValue = defaultValue;
    }

    /**
     *
     * @param name the name attribute
     * @param placeholder the placeholder attribute
     * @param label the inside of label tag
     * @param roles the roles who can see this input
     * @param endpoint the url leading to REST endpoint where options can be requested from
     * @param responseFormat the expected format of response
     */
    public SelectManyEndpointOptionsInput(String name, String placeholder, String label, Collection<String> roles, String endpoint, ResponseFormat responseFormat) {
        super(name, placeholder, label, roles, endpoint, responseFormat);
    }

    /**
     *
     * @return the preselected values
     */
    public Collection<Option> getDefaultValue() {
        return defaultValue;
    }

    /**
     *
     * @param defaultValue the preselected values, can be null
     */
    public void setDefaultValue(Collection<Option> defaultValue) {
        this.defaultValue = defaultValue;
    }

    @Override
    public String getInputType() {
        return inputType;
    }
}
