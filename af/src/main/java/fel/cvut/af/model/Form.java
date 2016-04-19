package fel.cvut.af.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import fel.cvut.af.model.input.Input;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;

/**
 * An entity class for Form element.
 * <p>
 * This class represents single form.
 * The form is created by providing collection of inputs.
 * @see Input and his descendants
 *
 */
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "name")
@NamedQueries({
        @NamedQuery(name = "Form.findByName", query = "SELECT f FROM Form f WHERE f.name = :name"),
        @NamedQuery(name = "Form.findAll", query = "SELECT f FROM Form f")
})
public class Form {

    /**
     * The unique value generated by JPA provider
     */
    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;

    /**
     * The name of form, unique.
     */
    @NotNull
    @Column(unique = true)
    private String name;

    /**
     * The inputs of this form.
     */
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE, mappedBy = "form")
    private Collection<Input> inputs;

    /**
     * The HTML class attribute of form.
     */
    @JsonProperty("formClass")
    private String htmlClassOfForm = "pure-form pure-form-aligned";

    /**
     * The HTML class attribute of div surrounding submit button.
     */
    @JsonProperty("buttonSurroundingClass")
    private String htmlClassOfButtonSurroundingDiv = "pure-controls";

    /**
     * The HTML class attribute of submit button.
     */
    @JsonProperty("submitButtonClass")
    private String htmlClassOfSubmitButton = "pure-button pure-button-primary";

    /**
     * The HTML class attribute of form error message div.
     */
    @JsonProperty("formErrorClass")
    private String htmlClassOfFormErrorMessageDiv = "form-error";

    public Form(){};

    /**
     * Creates instance of form with given name.
     *
     * @param name the unique name of form
     */
    public Form(String name) {
        this.name = name;
    }

    /**
     * Creates soft copy of given form.
     * Inputs are not copied.
     *
     * @param form the form to be copied
     */
    public Form(Form form) {
        this.name = form.getName();
        this.inputs = form.getInputs();
    }

    /**
     *
     * @return the unique identification value of this form
     */
    public Long getId() {
        return id;
    }

    /**
     *
     * @param id is unique identification value
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     *
     * @return the unique name of form
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name must be unique
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return the collection of inputs of this form
     */
    public Collection<Input> getInputs() {
        return inputs;
    }

    /**
     *
     * @param inputs that this form is created from
     */
    public void setInputs(Collection<Input> inputs) {
        this.inputs = inputs;
    }

    /**
     *
     * @return the html class attribute of form
     */
    public String getHtmlClassOfForm() {
        return htmlClassOfForm;
    }

    /**
     *
     * @param htmlClassOfForm is html class attribute of form
     */
    public void setHtmlClassOfForm(String htmlClassOfForm) {
        this.htmlClassOfForm = htmlClassOfForm;
    }

    /**
     *
     * @return the html class attribute of control panel's div
     */
    public String getHtmlClassOfButtonSurroundingDiv() {
        return htmlClassOfButtonSurroundingDiv;
    }

    /**
     *
     * @param htmlClassOfButtonSurroundingDiv is html class attribute of control panel's div
     */
    public void setHtmlClassOfButtonSurroundingDiv(String htmlClassOfButtonSurroundingDiv) {
        this.htmlClassOfButtonSurroundingDiv = htmlClassOfButtonSurroundingDiv;
    }

    /**
     *
     * @return the html class attribute of submit button
     */
    public String getHtmlClassOfSubmitButton() {
        return htmlClassOfSubmitButton;
    }

    /**
     *
     * @param htmlClassOfSubmitButton is html class attribute of submit button
     */
    public void setHtmlClassOfSubmitButton(String htmlClassOfSubmitButton) {
        this.htmlClassOfSubmitButton = htmlClassOfSubmitButton;
    }

    /**
     *
     * @return the html class attribute of form's error message div
     */
    public String getHtmlClassOfFormErrorMessageDiv() {
        return htmlClassOfFormErrorMessageDiv;
    }

    /**
     *
     * @param htmlClassOfFormErrorMessageDiv is html class attribute of form's error message div
     */
    public void setHtmlClassOfFormErrorMessageDiv(String htmlClassOfFormErrorMessageDiv) {
        this.htmlClassOfFormErrorMessageDiv = htmlClassOfFormErrorMessageDiv;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Form form = (Form) o;

        return name.equals(form.name);

    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
