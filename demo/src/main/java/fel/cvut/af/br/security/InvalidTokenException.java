package fel.cvut.af.br.security;

/**
 * Created by zb on 11.1.16.
 */
public class InvalidTokenException extends Exception {
    public InvalidTokenException(String username) {
        super("Invalid token for "+username+".");
    }
}
