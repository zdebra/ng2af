package fel.cvut.af.br.security;

import fel.cvut.af.br.model.User;
import fel.cvut.af.br.data.UserDAO;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Date;
import java.util.Random;

/**
 * Created by zb on 11.1.16.
 */
@Stateless
public class LogInService {

    private static final int EXPIRATION_PERIOD = 600000; // in miliseconds

    @Inject
    private UserDAO userDAO;

    public String authenticate(String username, String password) throws BadLoginException {

        User user = userDAO.findByName(username);

        if(user == null || !user.getPassword().equals(password)) {
            throw new BadLoginException(username);
        }

        // correct, lets generate token and expiration time
        String token = generateToken();
        Date now = new Date();
        user.setToken(token);
        user.setExpirationTime(new Date(now.getTime() + EXPIRATION_PERIOD));
        userDAO.update(user);

        return token;

    }

    public User validateToken(String token) throws InvalidTokenException, TokenExpiredException {

        User user = userDAO.findByToken(token);

        if(user == null) {
            throw new InvalidTokenException(user.getUsername());
        }

        Date now = new Date();
        Date toCompare = user.getExpirationTime();
        if(toCompare == null || now.compareTo(toCompare) >= 0) {
            throw new TokenExpiredException(user.getUsername());
        }

        // everything is correct, lets reset expiration time
        user.setExpirationTime(new Date(now.getTime() + EXPIRATION_PERIOD));
        userDAO.update(user);

        return userDAO.find(user.getId());
    }

    private String generateToken() {
        Random random = new SecureRandom();
        return new BigInteger(130, random).toString(32);
    }

    public void logout(String username) throws BadLoginException {

        User user = userDAO.findByName(username);

        if(user == null) {
            throw new BadLoginException(username);
        }

        user.setExpirationTime(new Date());
        user.setToken(null);
        userDAO.update(user);
    }


}
