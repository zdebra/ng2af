package fel.cvut.af.data;

import fel.cvut.af.model.input.Input;
import fel.cvut.af.model.validator.Validator;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by zdebra on 5.3.16.
 */
public class ValidatorDAO extends GenericDAOImpl<Validator> {

    public ValidatorDAO() {
        super(Validator.class);
    }

    public ArrayList<Validator> createCollection(Collection<Validator> validators, Input input) {

        ArrayList<Validator> output = new ArrayList<>(validators.size());
        for(Validator validator : validators) {
            validator.setInput(input);
            output.add(create(validator));
        }

        return output;

    }
}
