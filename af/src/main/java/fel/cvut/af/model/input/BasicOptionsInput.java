package fel.cvut.af.model.input;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import fel.cvut.af.model.options.Option;
import fel.cvut.af.util.serializer.OptionCollectionSerializer;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import java.util.Collection;

/**
 * An abstract class defines collection of {@code Option} for {@code Input} class.
 * <p>
 * It is used for inputs where could be chosen from multiple options.
 * For example {@code SelectMany} or {@code RadioButtonGroup}.
 */
@MappedSuperclass
public abstract class BasicOptionsInput extends Input {

    /**
     * The options of input
     */
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "input")
    @JsonSerialize(using = OptionCollectionSerializer.class)
    private Collection<Option> options;

    /**
     * No params constructor
     */
    public BasicOptionsInput() {
        super();
    }

    /**
     *
     * @param name the name attribute
     * @param placeholder the placeholder attribute
     * @param label the inside of label tag
     * @param roles the roles who can see this input
     * @param options the options which will be chosen from
     * @param responseFormat the expected format of response
     */
    public BasicOptionsInput(String name, String placeholder, String label, Collection<String> roles, Collection<Option> options, ResponseFormat responseFormat) {
        super(name, placeholder, label, roles, responseFormat);
        this.options = options;
    }

    /**
     * Simple getter.
     *
     * @return the collection of {@code Option} of this input
     */
    public Collection<Option> getOptions() {
        return options;
    }

    /**
     * Simple setter.
     *
     * @param options the options which will be chosen from
     */
    public void setOptions(Collection<Option> options) {
        this.options = options;
    }
}
