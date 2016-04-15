package fel.cvut.af.br.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import fel.cvut.af.model.options.EntityOption;
import fel.cvut.af.model.options.Option;
import fel.cvut.af.model.options.OptionAttribute;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by zb on 20.2.16.
 */
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
@NamedQueries({
        @NamedQuery(name = "Author.findByName", query = "SELECT a FROM Author a WHERE a.name = :name"),
        @NamedQuery(name = "Author.findAll", query = "SELECT a FROM Author a")
})
public class Author implements OptionAttribute {

    public static final String GET_ALL_OPTIONS_ENDPOINT = "/rest/book/author/options";
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String name;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name="user_id")
    @JsonIdentityReference(alwaysAsId = true)
    private User added;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "author")
    @JsonIdentityReference(alwaysAsId = true)
    private Collection<Book> booksWritten;

    public Author() {};

    public Author(String name) {
        this.name = name;
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

    public User getAdded() {
        return added;
    }

    public void setAdded(User added) {
        this.added = added;
    }

    public Collection<Book> getBooksWritten() {
        return booksWritten;
    }

    public void setBooksWritten(Collection<Book> booksWritten) {
        this.booksWritten = booksWritten;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Author author = (Author) o;

        return name.equals(author.name);

    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    @JsonIgnore
    public Option getOption() {
        return new EntityOption(getName(),getName(),getId());
    }

    public void addBookWritten(Book book) {
        if(this.booksWritten == null) {
            this.booksWritten = new ArrayList<>();
        }
        this.booksWritten.add(book);
    }
}
