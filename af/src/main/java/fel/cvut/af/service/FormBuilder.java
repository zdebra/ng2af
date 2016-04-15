package fel.cvut.af.service;

import fel.cvut.af.model.Form;
import fel.cvut.af.util.LoggingException;

/**
 * An interface for service class.
 * <p>
 * Methods allowing basics operation with form.
 */
public interface FormBuilder {

    /**
     * Builds existed form for given role.
     * <p>
     * Hides all inputs which should be hidden from provided role.
     *
     * @param name is unique name for form
     * @param role is role for who this form is builded for
     * @return the non-persisted instance of Form for given role, just ready to be serialized
     * @throws LoggingException when form of given name doesn't exist
     */
    Form buildForm(String name, String role) throws LoggingException;

    /**
     * Creates, persists new form.
     * <p>
     * It takes care of all provided inputs (as a form object attribute).
     *
     * @param form is the form object to be created
     * @throws LoggingException when form of given name already exists
     */
    void createForm(Form form) throws LoggingException;

    /**
     * Updates form values.
     *
     * @param form to be updated
     * @throws LoggingException when given form doesn't exist or some else updating issue
     */
    void updateForm(Form form) throws LoggingException;

    /**
     * Removes form from system.
     *
     * @param form to be deleted
     * @throws LoggingException when given form doesn't exist
     */
    void removeForm(Form form) throws LoggingException;

}
