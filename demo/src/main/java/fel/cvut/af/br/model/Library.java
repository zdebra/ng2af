package fel.cvut.af.br.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.text.ParseException;

/**
 * Created by zdebra on 8.3.16.
 */
@Entity
@DiscriminatorValue("LIBRARY")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
@NamedQueries({
        @NamedQuery(name = "Library.findAll", query = "SELECT l FROM Library l")
})
public class Library extends Place {

    @Transient
    public static final String GET_ALL_OPTIONS_ENDPOINT = "/rest/place/library/options";

    public Library() {
        super();
    }

    public Library(String name, Address address) {
        super(name, address);
    }

    public Library(@JsonProperty("name") String name,
                 @JsonProperty("street") String street,
                 @JsonProperty("city") String city,
                 @JsonProperty("state") String state,
                 @JsonProperty("zip") String zip) throws ParseException {
        setName(name);
        setAddress(new Address(street,city,state,zip));
    }
}
