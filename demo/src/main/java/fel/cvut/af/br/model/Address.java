package fel.cvut.af.br.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 * Created by zb on 20.2.16.
 */
@Embeddable
public class Address {

    @NotNull
    @NotEmpty
    private String street;

    @NotNull
    @NotEmpty
    private String city;

    @NotNull
    @NotEmpty
    private String state;

    @NotNull
    @NotEmpty
    private String zip;

    public Address(){};

    public Address(String street, String city, String state, String zip) {
        this.street = street;
        this.city = city;
        this.state = state;
        this.zip = zip;
    }


    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

}
