package fel.cvut.af.br.service;

import fel.cvut.af.br.model.*;
import fel.cvut.af.data.OptionDAO;
import fel.cvut.af.model.input.*;
import fel.cvut.af.model.options.EntityOption;
import fel.cvut.af.model.options.Option;
import fel.cvut.af.model.validator.*;
import fel.cvut.af.service.FormBuilder;
import fel.cvut.af.br.data.UserDAO;
import fel.cvut.af.data.FormDAO;
import fel.cvut.af.data.InputDAO;
import fel.cvut.af.model.Form;
import fel.cvut.af.model.options.BooleanOption;
import fel.cvut.af.model.options.StringOption;
import fel.cvut.af.util.LoggingException;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by zb on 14.1.16.
 */
@Singleton
@Startup
public class StartupBean {

    @Inject
    private UserService userService;

    @Inject
    private UserDAO userDAO;

    @Inject
    private BookService bookService;

    @Inject
    private AuthorService authorService;

    @Inject
    private FormBuilder formBuilder;

    @Inject
    private OptionDAO optionDAO;

    @Inject
    private InputDAO inputDAO;

    @Inject
    private FormDAO formDAO;

    @Inject
    private PlaceService placeService;

    @PostConstruct
    private void startup() {

        mockUsers();
        mockBooks();
        mockPlaces();
        mockNewBookForm();
        mockNewPlaceForm();
        mockNewUserForm();

    }

    private void mockPlaces() {

        try {

            Library l1 = new Library("NTK",new Address("street1","Prague","cz","16000"));
            placeService.addPlace(l1);
            Library l2 = new Library("Narodni knihovna",new Address("street2","Prague","cz","11000"));
            placeService.addPlace(l2);
            Library l3 = new Library("Mestska knihovna",new Address("street3","Prague","cz","16000"));
            placeService.addPlace(l3);

            Store s1 = new Store("Tesco",new Address("street1","Prague","cz","16000"));
            placeService.addPlace(s1);
            Store s2 = new Store("Neoluxor",new Address("street2","Prague","cz","11000"));
            placeService.addPlace(s2);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void mockBooks() {

        try {

            User editor = userService.getUser("editor");
            User admin = userService.getUser("admin");

            Author author = new Author("Tonda");
            Author author1 = new Author("J.K.");
            Author author2 = new Author("Albert");
            authorService.addAuthor(author,admin);
            authorService.addAuthor(author1,admin);
            authorService.addAuthor(author2,admin);
            author = authorService.getAuthor(author.getName());

            Book b0 = new Book(editor, "b0", "asd", author, new Date(), "xcc454");
            bookService.addBook(b0,editor);

            Book b1 = new Book(editor, "b1", "zxcxzc", author, new Date(), "xaacc");
            bookService.addBook(b1,editor);

            Book b2 = new Book(editor, "b2", "wqew", author, new Date(), "xcxc");
            bookService.addBook(b2,editor);

            Book b3 = new Book(editor, "b3", "casac", author, new Date(), "xascc");
            bookService.addBook(b3,editor);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void mockUsers() {

        try {
            User admin = new User("admin", "12345678", "admin@br.cz", new Date(), Role.ADMIN);
            userDAO.create(admin);

            User u1 = new User("manager", "12345678", "manager@br.cz", new Date(), Role.MANAGER);
            userService.addUser(u1,admin);

            User u2 = new User("editor", "12345678", "editor@br.cz", new Date(), Role.EDITOR);
            userService.addUser(u2,admin);

            User u3 = new User("visitor", "12345678", "visitor@br.cz", new Date(), Role.VISITOR);
            userService.addUser(u3,admin);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void mockNewBookForm() {

        try {

            ArrayList<String> roles1 = new ArrayList<>(3);
            roles1.add(Role.ADMIN.toString());
            roles1.add(Role.MANAGER.toString());
            roles1.add(Role.EDITOR.toString());
            Input name = new TextInput("name","Enter name of book","Name",roles1,"def");
            name.addValidator(new RegExpValidator("Must end with abc", ".*abc$"));

            Input description = new TextFieldInput("description", "Describe the book in a few words", "Description",roles1, "def");
            Input author = new SelectOneEndpointOptionsInput("author","Choose author of this book","Author", roles1,Author.GET_ALL_OPTIONS_ENDPOINT,authorService.getAuthor("Tonda").getOption(), ResponseFormat.VALUE);
            Input isnb = new TextInput("isnb", "Enter ISNB number", "ISNB", roles1, "def");
            isnb.addValidator(new MaxStringLengthValidator("You must enter max 8 chars",8));

            List<Option> statusOptions = BooleanOption.createOptions(new BooleanOption("Active","active",true),new BooleanOption("Inactive","inactive",false));
            Input status = new RadioButtonGroupInput("status","Select whether this book is active","Status",roles1,statusOptions,ResponseFormat.VALUE, statusOptions.get(0));

            ArrayList<String> roles2 = new ArrayList<>(2);
            roles2.add(Role.ADMIN.toString());
            roles2.add(Role.MANAGER.toString());

            List<Option> def1 = EntityOption.createOptions(placeService.getPlace("Tesco").getOption());
            List<Option> def2 = EntityOption.createOptions(placeService.getPlace("NTK").getOption(),placeService.getPlace("Narodni knihovna").getOption());

            Input stores = new SelectManyEndpointOptionsInput("stores","Choose stores where this book can be bought", "Stores",roles2, Store.GET_ALL_OPTIONS_ENDPOINT, ResponseFormat.VALUES,def1);
            Input price = new NumberInput("price", "Enter price of book", "Price",roles2);
            price.addValidator(new MaxNumberValidator("Price must be lower than 500",500));
            price.addValidator(new MinNumberValidator("Price must be higher than 100",100));

            Input libraries = new SelectManyEndpointOptionsInput("libraries","Choose libraries where this book can be borrowed", "Libraries",roles2, Library.GET_ALL_OPTIONS_ENDPOINT, ResponseFormat.VALUES,def2);

            ArrayList<String> roles3 = new ArrayList<>(1);
            roles3.add(Role.ADMIN.toString());
            Input dateAdded = new DateInput("dateAdded", "Enter a date when this book was added", "Date Added",roles3,new Date());
            dateAdded.addValidator(new DateUntilValidator("You can't add future dates",new Date()));

            List<Option> visibilityOptions = new ArrayList<>(Role.getAll().size());
            for (Role role : Role.getAll()) {
                if(!role.equals(Role.ADMIN)) {
                    visibilityOptions.add(new BooleanOption(role.getLabel(),role.toString(),true));
                }
            }

            Input visibility = new CheckboxGroupInput("visibility","Choose who see this book in system", "Visibility",roles3,visibilityOptions, ResponseFormat.OPTIONS,visibilityOptions);

            Collection<Input> inputs = new ArrayList<>(12);
            inputs.add(name);
            inputs.add(description);
            inputs.add(author);
            inputs.add(isnb);
            inputs.add(status);
            inputs.add(stores);
            inputs.add(price);
            inputs.add(libraries);
            inputs.add(visibility);
            inputs.add(dateAdded);

            Form form = new Form("newBookForm");
            form.setInputs(inputs);

            formBuilder.createForm(form);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void mockNewPlaceForm() {

        try {
            ArrayList<String> roles1 = new ArrayList<>(2);
            roles1.add(Role.ADMIN.toString());
            roles1.add(Role.MANAGER.toString());

            Input name = new TextInput("name","Enter name of place","Name",roles1);
            name.addValidator(new RequiredValidator("Name of place is required.",true));

            List<Option> types = StringOption.createOptions(new StringOption("Library","library","library"), new StringOption("Store", "store", "store"));
            Input placeType = new RadioButtonGroupInput("placeType","Place Type","Type",roles1,types,ResponseFormat.VALUE,types.get(0));

            Input street = new TextInput("street","Street","Street",roles1);
            street.addValidator(new RequiredValidator("Street is required",true));
            Input city = new TextInput("city","City","City",roles1);
            city.addValidator(new RequiredValidator("City is required",true));
            Input state = new TextInput("state","State","State",roles1);
            state.addValidator(new RequiredValidator("State is required",true));
            Input zip = new TextInput("zip","Zip","Zip",roles1);
            zip.addValidator(new RequiredValidator("Zip is required",true));


            Collection<Input> formInputs = new ArrayList<>();
            formInputs.add(name);
            formInputs.add(placeType);
            formInputs.add(street);
            formInputs.add(city);
            formInputs.add(state);
            formInputs.add(zip);

            Form form = new Form("newPlaceForm");
            form.setInputs(formInputs);

            formBuilder.createForm(form);
        } catch (LoggingException e) {
            e.printStackTrace();
        }

    }

    private void mockNewUserForm() {
        try {
            ArrayList<String> roles1 = new ArrayList<>(1);
            roles1.add(Role.ADMIN.toString());

            Input username = new TextInput("username","Type username","Username",roles1);
            username.addValidator(new RequiredValidator("Username is required",true));
            username.addValidator(new MinStringLengthValidator("Username must be at least 5 characters long",5));

            Input password = new PasswordInput("password","Type password","Password",roles1);
            password.addValidator(new RequiredValidator("Password is required",true));
            password.addValidator(new MinStringLengthValidator("Password must be at least 8 characters long",8));

            Input email = new TextInput("email","Type email","Email",roles1);
            email.addValidator(new EmailValidator("Insert valid email address"));

            Collection<Role> roles = Role.getAll();
            List<Option> roleOptions = new ArrayList<>(roles.size());
            for(Role role : roles) {
                if(!role.equals(Role.ADMIN)) {
                    roleOptions.add(new StringOption(role.getLabel(), role.toString(), role.toString()));
                }
            }
            Input userRole = new SelectOneBasicOptionsInput("role","Insert user role", "Role",roles1,roleOptions,ResponseFormat.VALUE,roleOptions.get(0));

            Collection<Input> formInputs = new ArrayList<>();
            formInputs.add(username);
            formInputs.add(password);
            formInputs.add(email);
            formInputs.add(userRole);

            Form form = new Form("newUserForm");
            form.setInputs(formInputs);

            formBuilder.createForm(form);
        } catch (LoggingException e) {
            e.printStackTrace();
        }

    }


}
