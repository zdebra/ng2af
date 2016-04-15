package fel.cvut.af.br.security;

import fel.cvut.af.br.model.User;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

/**
 * Created by zb on 11.1.16.
 * AUTHENTICATION priorty is executed before AUTHORIZATION priority
 */
@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {

    @Inject
    private LogInService logInService;

    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {

        String authorizationHeader = containerRequestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        if(authorizationHeader == null || !authorizationHeader.startsWith("Bearer")) {
            throw new NotAuthorizedException("Authorization header must be provided");
        }

        String tokenProvided = authorizationHeader.substring("Bearer".length()).trim();

        User user = null;
        try {
            user = logInService.validateToken(tokenProvided);
        } catch (Exception e) {
            containerRequestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }

        if (user==null) {
            throw new NotAuthorizedException("Can't found user.");
        }

        containerRequestContext.setSecurityContext(new CustomSecurityContext(user));




    }
}
