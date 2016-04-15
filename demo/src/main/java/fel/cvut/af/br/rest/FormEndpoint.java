package fel.cvut.af.br.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import fel.cvut.af.br.model.User;
import fel.cvut.af.br.security.Secured;
import fel.cvut.af.model.Form;
import fel.cvut.af.service.FormBuilder;


import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zdebra on 28.2.16.
 */

@Path("/form")
@Secured
@RequestScoped
public class FormEndpoint {

    @Inject
    private FormBuilder formBuilder;

    @GET
    @Produces("application/json")
    @Path("{formName}")
    public Response getForm(@PathParam("formName") String formName, @Context SecurityContext securityContext) {

        Response.ResponseBuilder builder = null;

        try {

            User user = (User) securityContext.getUserPrincipal();
            Form form = formBuilder.buildForm(formName,user.getRole().toString());

            ObjectMapper objectMapper = new ObjectMapper();
            String jsonString = objectMapper.writeValueAsString(form);

            builder = Response.ok().entity(jsonString);

        } catch (Exception e) {
            e.printStackTrace();
            Map<String, String> responseObj = new HashMap<>();
            responseObj.put("error", e.getMessage());
            builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
        }


        return builder.build();

    }

}
