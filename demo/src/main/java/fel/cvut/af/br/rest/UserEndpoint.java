package fel.cvut.af.br.rest;

import fel.cvut.af.br.model.User;
import fel.cvut.af.br.security.Secured;
import fel.cvut.af.model.options.Option;
import fel.cvut.af.br.model.Role;
import fel.cvut.af.br.service.UserService;
import fel.cvut.af.br.util.JsonUtil;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zb on 11.1.16.
 */
@Path("/user")
@RequestScoped
public class UserEndpoint {

    @Inject
    private UserService userService;

    @GET
    @Path("/me")
    @Produces("application/json")
    @Secured
    public Response getMe(@Context SecurityContext securityContext) {

        Response.ResponseBuilder builder = null;

        try {

            User user = (User) securityContext.getUserPrincipal();
            if(user==null) {
                throw new NotFoundException("User wasn't found");
            }

            builder = Response.ok().entity(user);

        } catch (NotFoundException e) {
            Map<String, String> responseObj = new HashMap<>();
            responseObj.put("error", e.getMessage());
            builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
        }

        return builder.build();

    }

    @GET
    @Path("/options")
    @Produces("application/json")
    @Secured
    public Response getOptions() {
        Response.ResponseBuilder builder = null;

        try {

            List<Option> options = new ArrayList<>();
            for(User user : userService.getAll()) {
                options.add(user.getOption());
            }

            builder = Response.ok().entity(JsonUtil.optionsToJsonObject(options));

        } catch (Exception e) {
            Map<String, String> responseObj = new HashMap<>();
            responseObj.put("error", e.getMessage());
            builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
        }

        return builder.build();

    }

    @POST
    @Secured({Role.ADMIN})
    @Produces("application/json")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response createUser(@Context SecurityContext securityContext, User user) {

        Response.ResponseBuilder builder;

        try {
            user.setId(null);
            userService.addUser(user, (User) securityContext.getUserPrincipal());
            builder = Response.ok();

        } catch (Exception e) {
            Map<String, String> responseObj = new HashMap<>();
            responseObj.put("error", e.getMessage());
            builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
        }

        return builder.build();

    }

}
