package fel.cvut.af.service;

import fel.cvut.af.data.FormDAO;
import fel.cvut.af.data.InputDAO;
import fel.cvut.af.data.OptionDAO;
import fel.cvut.af.data.ValidatorDAO;
import fel.cvut.af.model.Form;
import fel.cvut.af.model.input.*;
import fel.cvut.af.model.options.Option;
import fel.cvut.af.model.validator.Validator;
import fel.cvut.af.util.LoggingException;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Logger;

/**
 * Standard FormBuilder implementation.
 * <p>
 * @see FormBuilder
 */
@Stateless
public class StandardFormBuilder implements FormBuilder {

    @Inject
    private Logger logger;

    @Inject
    private FormDAO formDAO;

    @Inject
    private InputDAO inputDAO;

    @Inject
    private OptionDAO optionDAO;

    @Inject
    private ValidatorDAO validatorDAO;

    /**
     * Returns non-persisted entity of Form
     *
     * @param name
     * @param role
     * @return
     * @throws LoggingException
     */
    @Override
    public Form buildForm(String name, String role) throws LoggingException {

        if(name==null || role==null) {
            throw new LoggingException("Bad input arguments while building a form",logger);
        }

        Form form;
        Collection<Input> newInputs;

        try {
            form = formDAO.findByName(name);
            if(form==null) {
                throw new Exception("Form " + name + " doesn't exist while building a form");
            }

            Collection<Input> inputs = form.getInputs();
            newInputs = new ArrayList<>(inputs.size());

            for (Input input : inputs) {
                if (isForRole(input, role)) {
                    newInputs.add(input);
                }
            }
        } catch (Exception e) {
            throw new LoggingException(e.getMessage(), logger);
        }

        Form formCopy = new Form(form);
        formCopy.setInputs(newInputs);
        return formCopy;

    }

    private boolean isForRole(Input input, String role) throws Exception{
        Collection<String> inputRoles = input.getRoles();
        if(inputRoles==null) {
            throw new Exception("Input roles can't be null");
        }

        for(String r : inputRoles) {
            if(r.equals(role)) {
                return true;
            }
        }
        return false;
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void createForm(Form form) throws LoggingException {

        if(form==null) {
            throw new LoggingException("Bad input arguments provided while creating a form", logger);
        }

        try {
            Form existing = formDAO.findByName(form.getName());
            if(existing != null) {
                throw new Exception("Form " + existing.getName() + " already exists");
            }
        } catch (Exception e) {
            throw new LoggingException(e.getMessage(),logger);
        }

        Collection<Input> inputs = form.getInputs();
        if(inputs==null || inputs.size()==0) {
            throw new LoggingException("New form must have at least one input",logger);
        }

        form.setInputs(null);
        formDAO.create(form);

        Collection<Input> newInputs = new ArrayList<>(inputs.size());
        try {
            for (Input input : inputs) {

                Input in = createInput(input, form);
                newInputs.add(in);

            }
        } catch (Exception e) {
            throw new LoggingException(e.getMessage(), logger);
        }

        form = formDAO.find(form.getId());
        form.setInputs(newInputs);
        formDAO.update(form);

    }

    private Input createInput(Input input, Form form) throws Exception{

        if(input==null || form==null) {
            throw new Exception("Bad input arguments");
        }
        if(input.getName() == null) {
            throw new Exception("Name of input must be provided");
        }
        if(input.getLabel() == null) {
            throw new Exception("Label field of input must be provided");
        }

        if(input.getInputType() == null) {
            throw new Exception("Input type of field must be provided");
        }

        Collection<String> roles = input.getRoles();
        if(roles==null || roles.size()==0) {
            throw new Exception("SystemRole field of input must have at least one role provided");
        }

        input.setForm(form);

        Collection<Validator> validators = input.getValidators();
        input.setValidators(null);

        inputDAO.create(input);

        input = inputDAO.find(input.getId());

        if(validators != null) {
            input.setValidators(validatorDAO.createCollection(validators, input));
            input = inputDAO.find(input.getId());
        }

        if(input instanceof BasicOptionsInput) {

            BasicOptionsInput basicOptionsInput = (BasicOptionsInput) input;
            for(Option o : basicOptionsInput.getOptions()) {
                o.setInput(input);
            }

        }

        if(input instanceof SelectManyBasicOptionsInput) {

            SelectManyBasicOptionsInput selectManyBasicOptionsInput = (SelectManyBasicOptionsInput) input;
            if(selectManyBasicOptionsInput.getDefaultValue() != null) {
                for(Option o : selectManyBasicOptionsInput.getDefaultValue()) {
                    o.setInputDefault(input);
                }
            }

        }


        if(input instanceof SelectManyEndpointOptionsInput) {

            SelectManyEndpointOptionsInput selectManyEndpointOptionsInput = (SelectManyEndpointOptionsInput) input;
            if(selectManyEndpointOptionsInput.getDefaultValue() != null) {
                for(Option o : selectManyEndpointOptionsInput.getDefaultValue()) {
                    o.setInputDefault(input);
                }
            }

        }


        if(input instanceof CheckboxGroupInput) {

            CheckboxGroupInput checkboxGroupInput = (CheckboxGroupInput) input;
            if(checkboxGroupInput.getDefaultValue() != null) {
                for(Option o : checkboxGroupInput.getDefaultValue()) {
                    o.setInputDefault(input);
                }
            }

        }


        return inputDAO.find(input.getId());

    }

    @Override
    public void updateForm(Form form) throws LoggingException {

        if(form == null) {
            throw new LoggingException("Wrong input arguments while updating a form", logger);
        }

        Form existing;
        try {

            existing = formDAO.find(form.getId());

            if(existing==null) {
                throw new Exception("Form doesn't exist while updating a form");
            }

        } catch (Exception e) {
            throw new LoggingException(e.getMessage(),logger);
        }

        ArrayList<Input> newInputs = new ArrayList<>();
        try {
            for (Input newInput : form.getInputs()) {

                newInputs.add(createInput(newInput, form));

            }
        } catch (Exception e) {
            throw new LoggingException(e.getMessage(),logger);
        }

        existing.setInputs(newInputs);
        existing.setName(form.getName());

        formDAO.update(existing);

    }

    @Override
    public void removeForm(Form form) throws LoggingException {

        if(form == null) {
            throw new LoggingException("Wrong input arguments while removing a form", logger);
        }

        try {

            form = formDAO.find(form.getId());

            if(form==null) {
                throw new Exception("Form doesn't exist while removing a form");
            }

        } catch (Exception e) {
            throw new LoggingException(e.getMessage(),logger);
        }

        formDAO.delete(form.getId());

    }
}
