package fel.cvut.af.br.data;

import fel.cvut.af.br.model.User;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

/**
 * Created by zb on 11.1.16.
 */
@ApplicationScoped
public class UserDAO extends GenericDAOImpl<User> {

    public UserDAO() {
        super(User.class);
    }

    public User findByName(String name) {

        List<User> users = this.em.createNamedQuery(User.class.getSimpleName()+".findByName", User.class)
                .setParameter("name",name).getResultList();

        if(users.size() == 0) {
            return null;
        } else {
            return users.get(0);
        }

    }

    public User findByToken(String token) {
        List<User> users = this.em.createNamedQuery(User.class.getSimpleName() + ".findByToken", User.class)
                .setParameter("token", token).getResultList();

        if (users.size() == 0) {
            return null;
        } else {
            return users.get(0);
        }
    }
}
