package fel.cvut.af.br.model;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import fel.cvut.af.br.util.deserializator.BookDateAddedDeserializer;
import fel.cvut.af.model.options.Option;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 * Created by zb on 11.1.16.
 */
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
@NamedQueries({
        @NamedQuery(name = "Book.findByName", query = "SELECT b FROM Book b WHERE b.name = :name"),
        @NamedQuery(name = "Book.findAll", query = "SELECT b FROM Book b"),
        @NamedQuery(name = "Book.searchByName", query = "SELECT b FROM Book b WHERE b.name LIKE CONCAT('%',:name,'%')"),
        @NamedQuery(name = "Book.searchByAuthor", query = "SELECT b FROM Book b WHERE b.author LIKE CONCAT('%',:author,'%')")
})
public class Book {
    public static final String GET_ALL_OPTIONS_ENDPOINT = "/rest/book/options";

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name="user_id")
    private User added;

    @NotNull
    @Size(min = 1)
    private String name;

    private String description;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name="author_id")
    private Author author;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonDeserialize(using = BookDateAddedDeserializer.class)
    private Date dateAdded;

    private String ISNB;

    //@JsonDeserialize(using = BookStoreCollectionDeserializer.class)
    @ManyToMany(mappedBy = "books", fetch = FetchType.EAGER)
    private Collection<Place> stores;

    //@JsonDeserialize(using = BookLibraryCollectionDeserializer.class)
    @ManyToMany(mappedBy = "books", fetch = FetchType.EAGER)
    private Collection<Place> libraries;

    //@JsonDeserialize(using = BookStatusDeserializer.class)
    private Boolean status = true;

    @Min(0)
    private Integer price;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @JoinTable(name = "tblVisibility", joinColumns = @JoinColumn(name = "bookID"))
    @Column(name = "role", nullable = true)
    @Enumerated(EnumType.STRING)
    private Collection<Role> visibility;

    public Book() {

    }

    public Book(User added, String name, String description, Author author, Date dateAdded, String ISNB) {
        this.added = added;
        this.name = name;
        this.description = description;
        this.author = author;
        this.dateAdded = dateAdded;
        this.ISNB = ISNB;
    }

    @JsonCreator
    public Book(@JsonProperty("name") String name,
                @JsonProperty("description") String description,
                @JsonProperty("isnb") String isnb,
                @JsonProperty("price") Integer price,
                @JsonProperty("dateAdded") Date dateAdded,
                @JsonProperty("status") Boolean status,
                @JsonProperty("stores") Collection<Long> storesIds,
                @JsonProperty("libraries") Collection<Long> librariesIds,
                @JsonProperty("userAdded") Long addedId,
                @JsonProperty("author") Long authorId,
                @JsonProperty("visibility") Collection<Option> visibilityOptions) throws ParseException {
        this.name = name;
        this.description = description;
        this.ISNB = isnb;
        this.price = price;
        this.dateAdded = dateAdded;
        this.status = status;

        if(storesIds != null) {
            this.stores = new ArrayList<>(storesIds.size());
            for(Long storeId : storesIds) {
                Place store = new Store();
                store.setId(storeId);
                this.stores.add(store);
            }
        }

        if(librariesIds != null) {
            this.libraries = new ArrayList<>(librariesIds.size());
            for(Long libraryId : librariesIds) {
                Place library = new Library();
                library.setId(libraryId);
                this.libraries.add(library);
            }
        }
        this.author = new Author();
        this.author.setId(authorId);

        this.added = new User();
        this.added.setId(addedId);

        if(visibilityOptions != null) {
            for(Option option : visibilityOptions) {
                if((Boolean)option.getValue()) {
                    this.addVisibilityRole(Role.fromString(option.getName()));
                }
            }
        }

    }

    public Collection<Role> getVisibility() {
        return visibility;
    }

    public void setVisibility(Collection<Role> visibility) {
        this.visibility = visibility;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public User getAdded() {
        return added;
    }

    public void setAdded(User added) {
        this.added = added;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }

    public String getISNB() {
        return ISNB;
    }

    public void setISNB(String ISNB) {
        this.ISNB = ISNB;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Collection<Place> getStores() {
        return stores;
    }

    public void setStores(Collection<Place> stores) {
        this.stores = stores;
    }

    public Collection<Place> getLibraries() {
        return libraries;
    }

    public void setLibraries(Collection<Place> libraries) {
        this.libraries = libraries;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public void addVisibilityRole(Role role) {

        if(this.visibility == null) {
            this.visibility = new ArrayList<>();
        }

        this.visibility.add(role);
    }

}
