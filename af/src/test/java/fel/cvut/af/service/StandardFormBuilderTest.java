package fel.cvut.af.service;

import fel.cvut.af.data.*;
import fel.cvut.af.model.Form;
import fel.cvut.af.model.input.*;
import fel.cvut.af.model.options.*;
import fel.cvut.af.model.validator.*;
import fel.cvut.af.util.LoggingException;
import fel.cvut.af.util.ResourcesProducers;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.spi.TestResult;
import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by zdebra on 27.2.16.
 */
@RunWith(Arquillian.class)
public class StandardFormBuilderTest {

    @Deployment
    public static Archive<?> deploy() {
        return ShrinkWrap.create(WebArchive.class, "test.war")
                .addClasses(TestResult.class, ResourcesProducers.class, Logger.class, FormBuilder.class,
                        LoggingException.class, FormDAO.class, Form.class, Input.class, GenericDAO.class,
                        StandardFormBuilder.class, GenericDAOImpl.class, InputDAO.class,
                        InputDAO.class, Input.class, Option.class, OptionDAO.class, BasicOptionsInput.class,
                        EndpointOptionsInput.class, ValidatorDAO.class, Validator.class, ResponseFormat.class,
                        TextInput.class, TextFieldInput.class, PasswordInput.class, SelectManyBasicOptionsInput.class,
                        SelectManyEndpointOptionsInput.class, CheckboxGroupInput.class, NumberInput.class,
                        StringOption.class, DateInput.class, SelectOneEndpointOptionsInput.class, RequiredValidator.class,
                        RegExpValidator.class,MaxNumberValidator.class,MinNumberValidator.class, MaxStringLengthValidator.class,
                        MinStringLengthValidator.class, EmailValidator.class, DateSinceValidator.class, DateUntilValidator.class,
                        BooleanValueValidator.class, DateValueValidator.class, IntegerValueValidator.class, StringValueValidator.class,
                        EntityOption.class, NumberOption.class, RadioButtonGroupInput.class, BooleanOption.class,
                        SelectOneBasicOptionsInput.class)
                .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsWebInfResource("test-ds.xml");
    }

    @Inject
    private FormDAO formDAO;

    @Inject
    private InputDAO inputDAO;


    @Inject
    private FormBuilder formBuilder;


    private Form mockBasicForm(Collection<String> roles, String formName)  {

        Input username = new TextInput("username","Enter your username", "Username", roles, "default");
        Input password = new PasswordInput("password","Enter your password", "Password", roles);

        Collection<Input> inputs = new ArrayList<>(2);
        inputs.add(username);
        inputs.add(password);

        Form form = new Form(formName);
        form.setInputs(inputs);

        return form;

    }

    private Form mockBasicForm(String formname)  {
        Collection<String> roles = new ArrayList<>(3);
        roles.add("admin");
        roles.add("editor");
        roles.add("manager");
        return mockBasicForm(roles,formname);
    }

    private Form mockComplexForm(String formName)  {

        ArrayList<String> roles1 = new ArrayList<>(3);
        roles1.add("admin");
        roles1.add("manager");
        roles1.add("editor");
        Input name = new TextInput("name","Enter name of book","Name",roles1);
        Input description = new TextFieldInput("description", "Describe the book in a few words", "Description",roles1);
        Input isnb = new TextInput("isnb", "Enter ISNB number", "ISNB", roles1);

        ArrayList<String> roles2 = new ArrayList<>(2);
        roles2.add("admin");
        roles2.add("manager");
        Input price = new NumberInput("price", "Enter price of book", "Price",roles2,5);

        Collection<Option> options = new ArrayList<>(4);
        options.add(new StringOption("l1","n1","v1"));
        options.add(new StringOption("l2","n2","v2"));
        options.add(new StringOption("l3","n3","v3"));
        options.add(new StringOption("l4","n4","v4"));

        Input test = new SelectManyBasicOptionsInput("testBasicOptionsInput","Test","test",roles2,options, ResponseFormat.OPTIONS);

        ArrayList<String> roles3 = new ArrayList<>(1);
        roles3.add("admin");
        Input dateAdded = new DateInput("dateAdded", "Enter a date when this book was added", "Date Added",roles3);
        Input userAdded = new SelectOneEndpointOptionsInput("userAdded","Choose who added this book","Added by",
                roles3, "endpoint", ResponseFormat.VALUE);

        Collection<Input> inputs = new ArrayList<>(5);
        inputs.add(name);
        inputs.add(description);
        inputs.add(isnb);
        inputs.add(price);
        inputs.add(test);
        inputs.add(dateAdded);
        inputs.add(userAdded);

        Form form = new Form(formName);
        form.setInputs(inputs);
        return form;
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void testBuildBasicForm() throws Exception {

        String formName = "form1";
        Collection<String> roles = new ArrayList<>(1);
        roles.add("admin");
        roles.add("editor");
        formBuilder.createForm(mockBasicForm(roles,formName));
        Form form = formDAO.findByName(formName);

        Form formForManager = formBuilder.buildForm(formName, "manager");
        Assert.assertEquals(form,formForManager);
        Assert.assertEquals(formForManager.getInputs().size(),0);

        Form formForAdmin = formBuilder.buildForm(formName, "admin");

        Assert.assertEquals(form,formForAdmin);
        Assert.assertEquals(formForAdmin.getInputs().size(),2);


    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void testCreateForm() throws Exception {

        String formName = "login_form";
        formBuilder.createForm(mockBasicForm(formName));
        Form created = formDAO.findByName(formName);

        Assert.assertEquals(created.getName(),formName);

        ArrayList<Input> createdInputs = new ArrayList<>(created.getInputs());
        Assert.assertEquals(createdInputs.size(),2);

        Input createdUsername = createdInputs.get(0);
        Assert.assertEquals(createdUsername.getName(),"username");
        Assert.assertEquals(createdUsername.getForm(),created);
        Assert.assertEquals(createdUsername.getInputType(),"TEXT");

        Collection<String> rolesCreated = createdUsername.getRoles();
        Assert.assertEquals(rolesCreated.size(),3);

    }


    @Test(expected = LoggingException.class)
    @Transactional(TransactionMode.ROLLBACK)
    public void testBuildBasicFormBothNull() throws Exception {

        formBuilder.buildForm(null,null);

    }

    @Test(expected = LoggingException.class)
    @Transactional(TransactionMode.ROLLBACK)
    public void testBuildBasicFormNameNull() throws Exception {

        formBuilder.buildForm(null, "editor");

    }


    @Test(expected = LoggingException.class)
    @Transactional(TransactionMode.ROLLBACK)
    public void testBuildBasicFormRoleNull() throws Exception {

        formBuilder.buildForm("testname",null);

    }


    @Test(expected = LoggingException.class)
    @Transactional(TransactionMode.ROLLBACK)
    public void testBuildBasicFormDoesntExist() throws Exception {

        formBuilder.buildForm("nonexisting form", "manager");

    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void testBuildComplexForm() throws Exception {

        String formName = "form2";
        formBuilder.createForm(mockComplexForm(formName));
        Form form = formDAO.findByName(formName);

        Form formForVisitor = formBuilder.buildForm(formName, "visitor");
        Form formForEditor = formBuilder.buildForm(formName, "editor");
        Form formForManager = formBuilder.buildForm(formName, "manager");
        Form formForAdmin = formBuilder.buildForm(formName, "admin");

        Assert.assertEquals(form,formForAdmin);
        Assert.assertEquals(form,formForManager);
        Assert.assertEquals(form,formForEditor);
        Assert.assertEquals(form,formForVisitor);

        Assert.assertEquals(formForVisitor.getInputs().size(),0);
        Assert.assertEquals(formForEditor.getInputs().size(),3);
        Assert.assertEquals(formForManager.getInputs().size(),5);
        Assert.assertEquals(formForAdmin.getInputs().size(),7);

        BasicOptionsInput test1 = null;
        for(Input input : formForManager.getInputs()) {
            if(input.getName().equals("testBasicOptionsInput")) {
                test1 = (BasicOptionsInput) input;
            }
        }
        Assert.assertEquals(4,test1.getOptions().size());

        EndpointOptionsInput test2 = null;
        for(Input input : formForAdmin.getInputs()) {
            if(input.getName().equals("userAdded")) {
                test2 = (EndpointOptionsInput) input;
            }
        }
        Assert.assertEquals("endpoint", test2.getEndpoint());
    }


    @Test(expected = LoggingException.class)
    public void testCreateFormNull() throws Exception {

        formBuilder.createForm(null);

    }

    @Test(expected = LoggingException.class)
    public void testUpdateFormNull() throws Exception {
        formBuilder.updateForm(null);
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void testUpdateFormWithoutChange() throws Exception {

        String formName = "test";
        formBuilder.createForm(mockBasicForm(formName));
        Form form = formDAO.findByName(formName);
        Form copy = new Form(form);
        formBuilder.updateForm(form);
        Form updated = formDAO.findByName(formName);

        Assert.assertEquals(copy.getName(),updated.getName());
        List<Input> originalInputs = (List<Input>) copy.getInputs();
        List<Input> toCompareInputs = (List<Input>) updated.getInputs();

        Assert.assertEquals(originalInputs.size(),toCompareInputs.size());

        for (int i = 0; i < originalInputs.size(); i++) {

            Assert.assertEquals(originalInputs.get(i),toCompareInputs.get(i));

        }

    }


    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void testUpdateFormName() throws Exception {

        String formName = "testupdateformname";
        String newName = "newname";
        formBuilder.createForm(mockBasicForm(formName));
        Form form = formDAO.findByName(formName);
        form.setName(newName);

        Form copy = new Form(form);
        formBuilder.updateForm(form);
        Form updated = formDAO.findByName(newName);

        Form old;
        try {
            old = formDAO.findByName(formName);
        } catch (Exception e) {
            old = null;
        }
        Assert.assertNull(old);

        Assert.assertEquals("newname",updated.getName());
        List<Input> originalInputs = (List<Input>) copy.getInputs();
        List<Input> toCompareInputs = (List<Input>) updated.getInputs();

        Assert.assertEquals(originalInputs.size(),toCompareInputs.size());

        for (int i = 0; i < originalInputs.size(); i++) {

            Assert.assertEquals(originalInputs.get(i),toCompareInputs.get(i));

        }

    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void testUpdateForm() throws Exception {

        String formName = "testupdateform";
        String newName = "newname";
        formBuilder.createForm(mockBasicForm(formName));
        Form form = formDAO.findByName(formName);
        form.setName(newName);

        List<Input> oldInputs = (List<Input>) form.getInputs();
        oldInputs.clear();

        List<String> allRoles = getAllRoles();

        oldInputs.add(new PasswordInput("password1","Enter your password", "Password",allRoles));
        oldInputs.add(new PasswordInput("password2","Enter your password", "Password",allRoles));
        oldInputs.add(new PasswordInput("password3","Enter your password", "Password",allRoles));
        oldInputs.add(new PasswordInput("password4","Enter your password", "Password",allRoles));

        form.setInputs(oldInputs);

        formBuilder.updateForm(form);
        Form updated = formDAO.findByName(newName);

        Assert.assertEquals(newName,updated.getName());

        Collection<Input> inputs = updated.getInputs();
        Assert.assertEquals(inputs.size(),4);
        for(Input input : inputs){
            Assert.assertEquals(input.getInputType(),"PASSWORD");
        }

    }


    private List<String> getAllRoles() {

        ArrayList<String> allRoles = new ArrayList<>(4);
        allRoles.add("admin");
        allRoles.add("manager");
        allRoles.add("editor");
        allRoles.add("visitor");
        return allRoles;

    }


    @Test(expected = LoggingException.class)
    public void testRemoveFormNull() throws Exception {

        formBuilder.removeForm(null);

    }

    @Transactional(TransactionMode.ROLLBACK)
    @Test(expected = LoggingException.class)
    public void testRemoveFormDoesntExist() throws Exception {

        Form form = new Form("non existing form");
        formBuilder.removeForm(form);

    }

    @Transactional(TransactionMode.ROLLBACK)
    @Test
    public void testRemoveForm() throws Exception {

        String formName = "testformremoval";
        formBuilder.createForm(mockBasicForm(formName));
        Form form = formDAO.findByName(formName);
        Collection<Input> inputs = form.getInputs();

        Assert.assertTrue(inputs.size() > 0);
        Assert.assertEquals(form.getName(),formName);

        formBuilder.removeForm(form);

        Form deleted = null;
        try {
            deleted = formDAO.findByName(formName);
        } catch (Exception e) {
            deleted = null;
        }

        Assert.assertNull(deleted);

        // inputs should be also removed
        for(Input input : inputs) {
            Input removed = null;
            Assert.assertNotNull(input.getId());
            try {
                removed = inputDAO.find(input.getId());
            } catch (Exception e) {
                removed = null;
            }
            Assert.assertNull(removed);
        }

    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void testValidators() throws LoggingException {

        Input input1 = new TextInput("testinput1","testplaceholder","label",getAllRoles());
        input1.addValidator(new RequiredValidator("Required message",true));
        Input input2 = new TextInput("testinput2","testplaceholder","label",getAllRoles());
        input2.addValidator(new DateSinceValidator("message",new Date()));
        Input input3 = new TextInput("testinput3","testplaceholder","label",getAllRoles());
        input3.addValidator(new DateUntilValidator("message", new Date()));
        Input input4 = new TextInput("testinput4","testplaceholder","label",getAllRoles());
        input4.addValidator(new EmailValidator("message"));
        Input input5 = new TextInput("testinput5","testplaceholder","label",getAllRoles());
        input5.addValidator(new MaxNumberValidator("message",5));
        Input input6 = new TextInput("testinput6","testplaceholder","label",getAllRoles());
        input6.addValidator(new MinNumberValidator("message",7));
        Input input7 = new TextInput("testinput7","testplaceholder","label",getAllRoles());
        input7.addValidator(new MaxStringLengthValidator("message",4));
        Input input8 = new TextInput("testinput8","testplaceholder","label",getAllRoles());
        input8.addValidator(new MinStringLengthValidator("message",78));
        Input input9 = new TextInput("testinput9","testplaceholder","label",getAllRoles());
        input9.addValidator(new RegExpValidator("message","pattern"));

        Collection<Input> inputs = new ArrayList<>();
        inputs.add(input1);
        inputs.add(input2);
        inputs.add(input3);
        inputs.add(input4);
        inputs.add(input5);
        inputs.add(input6);
        inputs.add(input7);
        inputs.add(input8);
        inputs.add(input9);


        String formName = "validatortestform";
        Form f = new Form(formName);
        f.setInputs(inputs);

        formBuilder.createForm(f);

        Form builded = formBuilder.buildForm(formName,"admin");

        ArrayList<Input> buildedInputs = (ArrayList<Input>) builded.getInputs();

        Assert.assertNotNull(builded);
        Assert.assertEquals(inputs.size(),buildedInputs.size());

        Assert.assertEquals(RequiredValidator.class,buildedInputs.get(0).getValidators().iterator().next().getClass());
        Assert.assertEquals(DateSinceValidator.class,buildedInputs.get(1).getValidators().iterator().next().getClass());
        Assert.assertEquals(DateUntilValidator.class,buildedInputs.get(2).getValidators().iterator().next().getClass());
        Assert.assertEquals(EmailValidator.class,buildedInputs.get(3).getValidators().iterator().next().getClass());
        Assert.assertEquals(MaxNumberValidator.class,buildedInputs.get(4).getValidators().iterator().next().getClass());
        Assert.assertEquals(MinNumberValidator.class,buildedInputs.get(5).getValidators().iterator().next().getClass());
        Assert.assertEquals(MaxStringLengthValidator.class,buildedInputs.get(6).getValidators().iterator().next().getClass());
        Assert.assertEquals(MinStringLengthValidator.class,buildedInputs.get(7).getValidators().iterator().next().getClass());
        Assert.assertEquals(RegExpValidator.class,buildedInputs.get(8).getValidators().iterator().next().getClass());

        Assert.assertEquals("message",buildedInputs.get(8).getValidators().iterator().next().getMessage());
        Assert.assertEquals("pattern",buildedInputs.get(8).getValidators().iterator().next().getValue());
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void testCheckboxGroupInput() throws LoggingException {

        List<Option> options = new ArrayList<>();
        options.add(new StringOption("l1","n1","v1"));
        options.add(new StringOption("l2","n2","v2"));
        options.add(new StringOption("l3","n3","v3"));
        List<Option> defaults = new ArrayList<>();
        defaults.add(options.get(0));
        defaults.add(options.get(2));
        CheckboxGroupInput input = new CheckboxGroupInput("checkboxes","placeholder","label",getAllRoles(),options,ResponseFormat.NAMES,defaults);


        List<Option> options2 = new ArrayList<>();
        options2.add(new EntityOption("l1","n1",new Long(1)));
        options2.add(new EntityOption("l2","n2",new Long(2)));
        options2.add(new EntityOption("l3","n3",new Long(3)));
        List<Option> defaults2 = new ArrayList<>();
        defaults2.add(options2.get(0));
        defaults2.add(options2.get(2));

        CheckboxGroupInput input2 = new CheckboxGroupInput("checkboxes2","placeholder","label",getAllRoles(),options2,ResponseFormat.NAMES,defaults2);
        Collection<Input> inputs = new ArrayList<>();
        inputs.add(input);
        inputs.add(input2);

        String formName = "testCheckbox";
        Form f = new Form(formName);
        f.setInputs(inputs);

        formBuilder.createForm(f);

        Form builded = formBuilder.buildForm(formName,"admin");

        Assert.assertEquals(inputs.size(),builded.getInputs().size());

        List<Input> buildedInputs = (List<Input>) builded.getInputs();

        CheckboxGroupInput buildedInput = (CheckboxGroupInput) buildedInputs.get(0);
        Assert.assertEquals(CheckboxGroupInput.class,buildedInput.getClass());
        Assert.assertEquals(input.getName(),buildedInput.getName());
        Assert.assertEquals(input.getPlaceholder(),buildedInput.getPlaceholder());
        Assert.assertEquals(input.getInputType(),buildedInput.getInputType());
        Assert.assertEquals(input.getResponseFormat(),buildedInput.getResponseFormat());
        Assert.assertEquals(input.getOptions().size(),buildedInput.getOptions().size());
        Assert.assertEquals(input.getDefaultValue().size(),buildedInput.getDefaultValue().size());
        Assert.assertEquals(StringOption.class,buildedInput.getDefaultValue().iterator().next().getClass());
        Assert.assertEquals(StringOption.class,buildedInput.getOptions().iterator().next().getClass());

        CheckboxGroupInput buildedInput2 = (CheckboxGroupInput) buildedInputs.get(1);
        Assert.assertEquals(CheckboxGroupInput.class,buildedInput2.getClass());
        Assert.assertEquals(input2.getName(),buildedInput2.getName());
        Assert.assertEquals(input2.getPlaceholder(),buildedInput2.getPlaceholder());
        Assert.assertEquals(input2.getInputType(),buildedInput2.getInputType());
        Assert.assertEquals(input2.getResponseFormat(),buildedInput2.getResponseFormat());
        Assert.assertEquals(input2.getOptions().size(),buildedInput2.getOptions().size());
        Assert.assertEquals(input2.getDefaultValue().size(),buildedInput2.getDefaultValue().size());
        Assert.assertEquals(EntityOption.class,buildedInput2.getDefaultValue().iterator().next().getClass());
        Assert.assertEquals(EntityOption.class,buildedInput2.getOptions().iterator().next().getClass());


    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void testRadioButtonGroupInput() throws LoggingException {

        List<Option> options = new ArrayList<>();
        options.add(new NumberOption("l1","n1",4));
        options.add(new NumberOption("l2","n2",754));

        RadioButtonGroupInput input = new RadioButtonGroupInput("radioname","radioplace","radiolabel",getAllRoles(),options,ResponseFormat.VALUE,options.get(0));
        List<Input> inputs = new ArrayList<>();
        inputs.add(input);

        String formName = "RadioButtonform";
        Form f = new Form(formName);
        f.setInputs(inputs);
        formBuilder.createForm(f);

        Form builded = formBuilder.buildForm(formName,"admin");

        Assert.assertEquals(inputs.size(),builded.getInputs().size());

        List<Input> buildedInputs = (List<Input>) builded.getInputs();

        RadioButtonGroupInput buildedInput = (RadioButtonGroupInput) buildedInputs.get(0);
        Assert.assertEquals(RadioButtonGroupInput.class,buildedInput.getClass());
        Assert.assertEquals(input.getName(),buildedInput.getName());
        Assert.assertEquals(input.getPlaceholder(),buildedInput.getPlaceholder());
        Assert.assertEquals(input.getInputType(),buildedInput.getInputType());
        Assert.assertEquals(input.getResponseFormat(),buildedInput.getResponseFormat());
        Assert.assertEquals(input.getOptions().size(),buildedInput.getOptions().size());
        Assert.assertEquals(input.getDefaultValue().getName(),buildedInput.getDefaultValue().getName());
        Assert.assertEquals(NumberOption.class,buildedInput.getDefaultValue().getClass());
        Assert.assertEquals(NumberOption.class,buildedInput.getOptions().iterator().next().getClass());

    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void testSelectManyBasicOptionsInput() throws LoggingException {

        List<Option> options = new ArrayList<>();
        options.add(new BooleanOption("l1","n1",true));
        options.add(new BooleanOption("l2","n2",false));
        options.add(new BooleanOption("l3","n3",false));

        SelectManyBasicOptionsInput input = new SelectManyBasicOptionsInput("testname","testplaceh","testlabel",getAllRoles(),options,ResponseFormat.OPTIONS,options);
        List<Input> inputs = new ArrayList<>();
        inputs.add(input);

        String formName = "selmanybasictest";
        Form f = new Form(formName);
        f.setInputs(inputs);

        formBuilder.createForm(f);

        Form builded = formBuilder.buildForm(formName,"admin");

        Assert.assertEquals(inputs.size(),builded.getInputs().size());
        SelectManyBasicOptionsInput buildedInput = (SelectManyBasicOptionsInput) builded.getInputs().iterator().next();

        Assert.assertEquals(input.getDefaultValue().size(),buildedInput.getDefaultValue().size());
        Assert.assertEquals(BooleanOption.class,buildedInput.getDefaultValue().iterator().next().getClass());
        Assert.assertEquals(BooleanOption.class,buildedInput.getOptions().iterator().next().getClass());
        Assert.assertEquals(input.getName(),buildedInput.getName());
        Assert.assertEquals(input.getResponseFormat(),buildedInput.getResponseFormat());


    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void testSelectManyEndpointOptionsInput() throws LoggingException {

        List<Option> options = new ArrayList<>();
        options.add(new BooleanOption("l1","n1",true));
        options.add(new BooleanOption("l2","n2",false));
        options.add(new BooleanOption("l3","n3",false));

        SelectManyEndpointOptionsInput input = new SelectManyEndpointOptionsInput("testname","testplaceh","testlabel",getAllRoles(),"endpoint",ResponseFormat.NAMES,options);
        List<Input> inputs = new ArrayList<>();
        inputs.add(input);

        String formName = "selmanyendpointform";
        Form f = new Form(formName);
        f.setInputs(inputs);

        formBuilder.createForm(f);

        Form builded = formBuilder.buildForm(formName,"admin");

        Assert.assertEquals(inputs.size(),builded.getInputs().size());
        SelectManyEndpointOptionsInput buildedInput = (SelectManyEndpointOptionsInput) builded.getInputs().iterator().next();

        Assert.assertEquals(input.getDefaultValue().size(),buildedInput.getDefaultValue().size());
        Assert.assertEquals(BooleanOption.class,buildedInput.getDefaultValue().iterator().next().getClass());
        Assert.assertEquals(input.getEndpoint(),buildedInput.getEndpoint());
        Assert.assertEquals(input.getName(),buildedInput.getName());
        Assert.assertEquals(input.getResponseFormat(),buildedInput.getResponseFormat());


    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void testSelectOneBasicOptionsInput() throws LoggingException {

        List<Option> options = new ArrayList<>();
        options.add(new NumberOption("l1","n1",1));
        options.add(new NumberOption("l2","n2",541));
        options.add(new NumberOption("l3","n3",45));

        SelectOneBasicOptionsInput input = new SelectOneBasicOptionsInput("testname","testplaceh","testlabel",getAllRoles(),options,ResponseFormat.NAME,options.get(0));
        List<Input> inputs = new ArrayList<>();
        inputs.add(input);

        String formName = "selonebasic";
        Form f = new Form(formName);
        f.setInputs(inputs);

        formBuilder.createForm(f);

        Form builded = formBuilder.buildForm(formName,"admin");

        Assert.assertEquals(inputs.size(),builded.getInputs().size());
        SelectOneBasicOptionsInput buildedInput = (SelectOneBasicOptionsInput) builded.getInputs().iterator().next();

        Assert.assertEquals(input.getDefaultValue().getName(),buildedInput.getDefaultValue().getName());
        Assert.assertEquals(NumberOption.class,buildedInput.getDefaultValue().getClass());
        Assert.assertEquals(NumberOption.class,buildedInput.getOptions().iterator().next().getClass());
        Assert.assertEquals(input.getName(),buildedInput.getName());
        Assert.assertEquals(input.getResponseFormat(),buildedInput.getResponseFormat());


    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void testSelectOneEndpointOptionsInput() throws LoggingException {

        List<Option> options = new ArrayList<>();
        options.add(new NumberOption("l1","n1",1));
        options.add(new NumberOption("l2","n2",541));
        options.add(new NumberOption("l3","n3",45));

        SelectOneEndpointOptionsInput input = new SelectOneEndpointOptionsInput("testname","testplaceh","testlabel",getAllRoles(),"endpoint",ResponseFormat.NAME);
        input.setDefaultValue(options.get(1));
        List<Input> inputs = new ArrayList<>();
        inputs.add(input);

        String formName = "seloneendpointform";
        Form f = new Form(formName);
        f.setInputs(inputs);

        formBuilder.createForm(f);

        Form builded = formBuilder.buildForm(formName,"admin");

        Assert.assertEquals(inputs.size(),builded.getInputs().size());
        SelectOneEndpointOptionsInput buildedInput = (SelectOneEndpointOptionsInput) builded.getInputs().iterator().next();

        Assert.assertEquals(input.getDefaultValue().getName(),buildedInput.getDefaultValue().getName());
        Assert.assertEquals(NumberOption.class,buildedInput.getDefaultValue().getClass());
        Assert.assertEquals(input.getEndpoint(),buildedInput.getEndpoint());
        Assert.assertEquals(input.getName(),buildedInput.getName());
        Assert.assertEquals(input.getResponseFormat(),buildedInput.getResponseFormat());

    }

}