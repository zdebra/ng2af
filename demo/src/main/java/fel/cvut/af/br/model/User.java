package fel.cvut.af.br.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import fel.cvut.af.model.options.Option;
import fel.cvut.af.model.options.EntityOption;
import fel.cvut.af.model.options.OptionAttribute;
import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import javax.security.auth.Subject;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.security.Principal;
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
        @NamedQuery(name = "User.findByName", query = "SELECT u FROM User u WHERE u.username = :name"),
        @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u"),
        @NamedQuery(name = "User.findByToken", query = "SELECT u FROM User u WHERE u.token = :token")
})
public class User implements Principal, OptionAttribute {

    public static final String GET_ALL_OPTIONS_ENDPOINT = "/rest/user/options";

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    @Size(min = 5, max = 25)
    private String username;

    @NotNull
    @Size(min = 8)
    @JsonIgnore
    private String password;

    @Email
    private String email;

    @JsonIgnore
    private String token;

    @JsonIgnore
    @Temporal(TemporalType.TIMESTAMP)
    private Date expirationTime;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastLoggedIn;

    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    @NotNull
    private Role role = Role.VISITOR;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "added")
    private Collection<Book> booksAdded;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "added")
    private Collection<Author> authorsAdded;

    public User() {}

    public User(@JsonProperty("username") String username,
                @JsonProperty("email") String email,
                @JsonProperty("role") String strRole,
                @JsonProperty("password") String password) {

        this.username = username;
        this.password = password;
        this.email = email;
        this.created = new Date();
        this.role = Role.fromString(strRole);
        //new User(username,password,email,new Date(),Role.fromString(strRole));
    }


    public User(String username) {
        this.username = username;
    }

    public User(String username, String password, String email, Date created, Role role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.created = created;
        this.role = role;
    }

    public User(String username, String email, Role role) {
        this.username = username;
        this.email = email;
        this.role = role;
        this.created = new Date();

    }

    public Collection<Book> getBooksAdded() {
        return booksAdded;
    }

    public void setBooksAdded(Collection<Book> booksAdded) {
        this.booksAdded = booksAdded;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(Date expirationTime) {
        this.expirationTime = expirationTime;
    }

    public Date getLastLoggedIn() {
        return lastLoggedIn;
    }

    public void setLastLoggedIn(Date lastLoggedIn) {
        this.lastLoggedIn = lastLoggedIn;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Collection<Author> getAuthorsAdded() {
        return authorsAdded;
    }

    public void setAuthorsAdded(Collection<Author> authorsAdded) {
        this.authorsAdded = authorsAdded;
    }

    @Override
    public String getName() {
        return username;
    }

    @Override
    public boolean implies(Subject subject) {
        for(Principal p : subject.getPrincipals()) {
            if(p.equals(this)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return username.equals(user.username);

    }

    @Override
    public int hashCode() {
        return username.hashCode();
    }

    @Override
    public Option getOption() {

        return new EntityOption(getUsername(),getUsername(),getId());

    }

    public void addBook(Book book) {
        if (this.booksAdded == null) {
            this.booksAdded = new ArrayList<>();
        }
        this.booksAdded.add(book);
    }
}
