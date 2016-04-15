package fel.cvut.af.br.service;

import fel.cvut.af.br.model.User;

import java.util.Collection;

/**
 * Created by zb on 14.1.16.
 */
public interface UserService {

    void addUser(User user, User adding) throws Exception;

    User getUser(String username) throws Exception;

    User getUser(Long id) throws Exception;

    void editUser(User user, User editing) throws Exception;

    void removeUser(User user, User removing) throws Exception;

    Collection<User> getAll() throws Exception;

}
