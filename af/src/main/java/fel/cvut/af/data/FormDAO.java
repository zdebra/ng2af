package fel.cvut.af.data;

import fel.cvut.af.model.Form;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

/**
 * Created by zb on 20.2.16.
 */
@ApplicationScoped
public class FormDAO extends GenericDAOImpl<Form> {

    public FormDAO() {
        super(Form.class);
    }

    public Form findByName(String name) {
        List<Form> forms = this.em.createNamedQuery(Form.class.getSimpleName()+".findByName", Form.class)
                .setParameter("name",name).getResultList();

        if(forms.size() == 0) {
            return null;
        } else {
            return forms.get(0);
        }

    }
}
