package fel.cvut.af.br.service;

import fel.cvut.af.br.data.UserDAO;
import fel.cvut.af.br.model.Role;
import fel.cvut.af.br.model.User;
import fel.cvut.af.util.LoggingException;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.naming.NoPermissionException;
import java.util.Collection;
import java.util.logging.Logger;

/**
 * Created by zb on 14.1.16.
 */
@Stateless
public class StandardUserService implements UserService {

    @Inject
    private UserDAO userDAO;

    @Inject
    private Logger logger;

    @Override
    public void addUser(User user, User added) throws LoggingException, NoPermissionException {

        if(user==null) {
            throw new LoggingException("No user provided while adding new user", logger);
        }

        try {
            added = userDAO.find(added.getId());

            if (added == null) {
                throw new Exception("User not found");
            }
        } catch (Exception e) {
            throw new LoggingException("No user who adding new one provided while adding new user",logger);

        }

        if(added.getRole() != Role.ADMIN) {
            throw new NoPermissionException("User "+ added.getName() + " has no permission to add new user");
        }

        User existing = userDAO.findByName(user.getName());
        if(existing != null) {
            throw new LoggingException("User " +existing.getName() + " already exists", logger);
        }

        userDAO.create(user);

    }

    @Override
    public User getUser(String username) throws LoggingException {

        if(username == null) {
            throw new LoggingException("No username provided", logger);
        }
        User user;

        try {

            user = userDAO.findByName(username);

            if(user==null) {
                throw new Exception("User " +username+ " doesn't exist while getting user");
            }

        } catch (Exception e) {
            throw new LoggingException(e.getMessage(),logger);
        }


        return user;

    }

    @Override
    public User getUser(Long id) throws LoggingException {
        if(id == null) {
            throw new LoggingException("No id provided", logger);
        }

        User user;
        try {
            user = userDAO.find(id);

            if (user == null) {
                throw new Exception("User with id " + id + " doesn't exist");
            }
        } catch (Exception e) {
            throw new LoggingException(e.getMessage(), logger);
        }

        return user;
    }

    @Override
    public void editUser(User user, User editing) throws LoggingException, NoPermissionException {
        try {
            if(editing == null) {
                throw new Exception("User who editing isn't provided while editing");
            }

            editing = userDAO.find(editing.getId());
            if(editing == null) {
                throw new Exception("User who editing doesn't exist while editing");
            }
        } catch (Exception e) {
            throw new LoggingException(e.getMessage(),logger);
        }

        if(editing.getRole() != Role.ADMIN) {
            throw new NoPermissionException("User "+ editing.getName() + " has no permission to edit user");
        }

        if(user == null) {
            throw new LoggingException("User not provided while editing user", logger);
        }

        try {
            User existing = userDAO.findByName(user.getName());
            if (existing.getId() != user.getId()) {
                throw new Exception("User named " + user.getName() + " already exists while editing user");
            }
        } catch (Exception e) {
            throw new LoggingException(e.getMessage(), logger);
        }


        userDAO.update(user);

    }

    @Override
    public void removeUser(User user, User removing) throws LoggingException, NoPermissionException {

        try {
            if (user == null) {
                throw new Exception("User not provided");
            }

            if (removing == null) {
                throw new Exception("User who removing user isn't provided");
            }

            removing = userDAO.find(removing.getId());

            if (removing == null) {
                throw new Exception("User who removing is not provided while removing");
            }
        } catch (Exception e) {
            throw new LoggingException(e.getMessage(), logger);
        }

        User existing;
        try {
            existing = userDAO.find(user.getId());
            if (existing == null) {
                throw new Exception("User doesn't exist");
            }
        } catch (Exception e) {
            throw new LoggingException(e.getMessage(), logger);
        }


        if(removing.getRole() != Role.ADMIN) {
            throw new NoPermissionException("User "+ removing.getName() + " has no permission to remove user");
        }

        userDAO.delete(existing.getId());

    }

    @Override
    public Collection<User> getAll() throws Exception {
        return userDAO.findAll();
    }
}
