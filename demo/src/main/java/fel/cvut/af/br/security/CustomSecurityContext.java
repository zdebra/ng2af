package fel.cvut.af.br.security;

import fel.cvut.af.br.model.User;

import javax.ws.rs.core.SecurityContext;
import java.security.Principal;

/**
 * Created by zb on 12.1.16.
 * Obtaining an instance of user who doing a request; recognized by token
 */
public class CustomSecurityContext implements SecurityContext {

    private final User user;

    public CustomSecurityContext(User user) {
        this.user = user;
    }

    @Override
    public Principal getUserPrincipal() {
        return user;
    }

    @Override
    public boolean isUserInRole(String s) {

        return user.getRole().toString().equals(s);

    }

    @Override
    public boolean isSecure() {
        return false;
    }

    @Override
    public String getAuthenticationScheme() {
        return null;
    }
}
