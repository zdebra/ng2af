package fel.cvut.af.model.input;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import fel.cvut.af.model.options.Option;
import fel.cvut.af.util.serializer.DefaultValueCollectionSerializer;

import javax.persistence.*;
import java.util.Collection;

/**
 * An entity class for Selectbox allowing multiple options to be selected.
 * <p>
 * The options can represent different value types, see {@link Option} and his descendants.
 * <p>
 * Use this class when {@code Options} hasn't too many elements.
 * There is also an implementation of SelectMany where {@code Options} can be defined as a url leading to REST endpoint.
 * See {@link SelectManyEndpointOptionsInput}.
 */
@Entity
@DiscriminatorValue("SELECT_MANY_BASIC")
public class SelectManyBasicOptionsInput extends BasicOptionsInput {

    /**
     * Constant which is used as a type of input and appears as an attribute of JSON serialized object.
     */
    @Transient
    private static final String inputType = "SELECT_MANY";

    /**
     * The preselected options.
     */
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "inputDefault")
    @JsonSerialize(using = DefaultValueCollectionSerializer.class)
    private Collection<Option> defaultValue;

    public SelectManyBasicOptionsInput() {
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
    public SelectManyBasicOptionsInput(String name, String placeholder, String label, Collection<String> roles, Collection<Option> options, ResponseFormat responseFormat, Collection<Option> defaultValue) {
        super(name, placeholder, label, roles, options, responseFormat);
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
    public SelectManyBasicOptionsInput(String name, String placeholder, String label, Collection<String> roles, Collection<Option> options, ResponseFormat responseFormat) {
        super(name, placeholder, label, roles, options, responseFormat);
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
     * @param defaultValue the preselected value, should be member of {@code Options}, can be null
     */
    public void setDefaultValue(Collection<Option> defaultValue) {
        this.defaultValue = defaultValue;
    }

    @Override
    public String getInputType() {
        return inputType;
    }
}
