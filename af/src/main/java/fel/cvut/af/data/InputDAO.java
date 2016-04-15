package fel.cvut.af.data;

import fel.cvut.af.model.input.Input;

import javax.enterprise.context.ApplicationScoped;

/**
 * Created by zb on 20.2.16.
 */
@ApplicationScoped
public class InputDAO extends GenericDAOImpl<Input> {

    public InputDAO() {
        super(Input.class);
    }

}
