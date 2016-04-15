package fel.cvut.af.br.rest;

import fel.cvut.af.br.security.Secured;
import fel.cvut.af.br.model.Credentials;
import fel.cvut.af.br.model.Token;
import fel.cvut.af.br.model.User;
import fel.cvut.af.br.security.LogInService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.naming.NoPermissionException;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

/**
 * Created by zb on 11.1.16.
 */
@Path("/auth")
@RequestScoped
public class AuthenticationEndpoint {

    @Inject
    private LogInService logInService;

    @Context
    private SecurityContext securityContext;

    @POST
    @Path("/login")
    @Produces("application/json")
    @Consumes("application/json")
    public Response login(Credentials credentials) {

        try {

            String username = credentials.getUsername();
            String password = credentials.getPassword();

            // Authenticate the user using the credentials provided
            String tokenStr = logInService.authenticate(username,password);

            Token token = new Token(tokenStr);

            // Return the token on the response
            return Response.ok().entity(token).build();

        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    @POST
    @Secured
    @Path("/logout")
    @Produces("application/json")
    @Consumes("application/json")
    public Response logout() {

        try {
            User user = (User) securityContext.getUserPrincipal();
            if(user == null) {
                throw new NoPermissionException("You can logout only yourself.");
            }

            logInService.logout(user.getUsername());

        } catch (Exception e) {
           return Response.status(Response.Status.BAD_REQUEST).build();
        }

        return Response.ok().build();

    }
}
