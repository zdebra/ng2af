package fel.cvut.af.br.model;

import com.fasterxml.jackson.annotation.*;
import fel.cvut.af.model.options.EntityOption;
import fel.cvut.af.model.options.Option;
import fel.cvut.af.model.options.OptionAttribute;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by zb on 20.2.16.
 */
@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
@DiscriminatorColumn(name = "PLACE_TYPE")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id", scope = Place.class)
@NamedQueries({
        @NamedQuery(name = "Place.findByName", query = "SELECT p FROM Place p WHERE p.name = :name")
})
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="placeType")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Store.class, name="store"),
        @JsonSubTypes.Type(value = Library.class, name="library")
})
public abstract class Place implements OptionAttribute {

    @GeneratedValue
    @Id
    @JsonProperty("id")
    private Long id;

    @NotNull
    @NotEmpty
    //@JsonProperty("name")
    private String name;

    @Embedded
    @JsonUnwrapped
    //@JsonProperty("address")
    private Address address;

    @ManyToMany(fetch = FetchType.EAGER)
    @JsonIgnore
    private Collection<Book> books;

    protected Place(){};

    protected Place(String name, Address address) {
        this.name = name;
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Collection<Book> getBooks() {
        return books;
    }

    public void setBooks(Collection<Book> books) {
        this.books = books;
    }

    @Override
    @JsonIgnore
    public Option getOption() {
        return new EntityOption(getName(),getName(),getId());
    }

    public void addBook(Book book) {
        if(this.books==null) {
            this.books = new ArrayList<>();
        }
        this.books.add(book);
    }
}
