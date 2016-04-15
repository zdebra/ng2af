package fel.cvut.af.br.security;

/**
 * Created by zb on 11.1.16.
 */
public class TokenExpiredException extends Exception {
    public TokenExpiredException(String username) {
        super("Token expired for " + username + ".");
    }
}
