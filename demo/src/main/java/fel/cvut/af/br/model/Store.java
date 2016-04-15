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
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id", scope = Place.class)
@DiscriminatorValue("STORE")
@NamedQueries({
        @NamedQuery(name = "Store.findAll", query = "SELECT s FROM Store s")
})
public class Store extends Place {

    @Transient
    public static final String GET_ALL_OPTIONS_ENDPOINT = "/rest/place/store/options";

    public Store(String name, Address address) {
        super(name, address);
    }

    public Store() {
        super();
    }

    public Store(@JsonProperty("name") String name,
                 @JsonProperty("street") String street,
                 @JsonProperty("city") String city,
                 @JsonProperty("state") String state,
                 @JsonProperty("zip") String zip) throws ParseException {
        this.setName(name);
        this.setAddress(new Address(street,city,state,zip));
    }
}
