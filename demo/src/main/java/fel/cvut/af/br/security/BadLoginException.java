package fel.cvut.af.br.security;

/**
 * Created by zb on 11.1.16.
 */
public class BadLoginException extends Exception {
    public BadLoginException(String username) {
        super("Wrong login for " + username + ".");
    }
}
