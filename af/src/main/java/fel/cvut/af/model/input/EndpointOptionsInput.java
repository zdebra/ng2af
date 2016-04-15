package fel.cvut.af.model.input;

import fel.cvut.af.model.options.Option;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import java.util.Collection;

/**
 * An abstract class which defines REST endpoint where options could be found.
 * <p>
 * This is used when options for input (f.e. {@code SelectMany}) is collection of bigger size and thus client app
 * should make another request for it.
 * The responded data should have format as serialized collection of {@link Option}.
 */
@MappedSuperclass
public abstract class EndpointOptionsInput extends Input {

    /**
     * Url leading to REST endpoint where input options can be requested from.
     */
    @NotNull
    @NotEmpty
    private String endpoint;

    public EndpointOptionsInput() {
        super();
    }

    /**
     *
     * @param name the name attribute
     * @param placeholder the placeholder attribute
     * @param label the inside of label tag
     * @param roles the roles who can see this input
     * @param endpoint the url leading to REST endpoint, not null, not empty
     * @param responseFormat the expected format of response
     */
    public EndpointOptionsInput(String name, String placeholder, String label, Collection<String> roles, String endpoint, ResponseFormat responseFormat) {
        super(name, placeholder, label, roles, responseFormat);
        this.endpoint = endpoint;
    }

    /**
     * Simple getter.
     *
     * @return the url leading to REST endpoint
     */
    public String getEndpoint() {
        return endpoint;
    }

    /**
     * Simple setter.
     *
     * @param endpoint the url leading to REST endpoint
     */
    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }
}
