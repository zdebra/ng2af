package fel.cvut.af.br.rest;

import fel.cvut.af.br.model.Library;
import fel.cvut.af.br.model.Place;
import fel.cvut.af.br.model.Role;
import fel.cvut.af.br.model.Store;
import fel.cvut.af.br.security.Secured;
import fel.cvut.af.br.service.PlaceService;
import fel.cvut.af.br.util.JsonUtil;
import fel.cvut.af.model.options.Option;

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
 * Created by Zdeněk Brabec
 * brabezd1@fel.cvut.cz
 * 17.3.16
 */
@Secured
@Path("/place")
@RequestScoped
public class PlaceEndpoint {

    @Inject
    private PlaceService placeService;

    @GET
    @Path("/store/options")
    @Produces("application/json")
    @Secured
    public Response getStoreOptions() {
        Response.ResponseBuilder builder = null;

        try {

            List<Option> options = new ArrayList<>();
            for(Place store : placeService.getAll(Store.class)) {
                options.add(store.getOption());
            }


            builder = Response.ok().entity(JsonUtil.optionsToJsonObject(options));

        } catch (Exception e) {
            Map<String, String> responseObj = new HashMap<>();
            responseObj.put("error", e.getMessage());
            builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
        }

        return builder.build();

    }


    @GET
    @Path("/store")
    @Produces("application/json")
    @Secured
    public Response getStores() {
        Response.ResponseBuilder builder = null;

        try {

            builder = Response.ok().entity(placeService.getAll(Store.class));

        } catch (Exception e) {
            Map<String, String> responseObj = new HashMap<>();
            responseObj.put("error", e.getMessage());
            builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
        }

        return builder.build();

    }

    @GET
    @Path("/library")
    @Produces("application/json")
    @Secured
    public Response getLibraries() {
        Response.ResponseBuilder builder = null;

        try {

            builder = Response.ok().entity(placeService.getAll(Library.class));

        } catch (Exception e) {
            Map<String, String> responseObj = new HashMap<>();
            responseObj.put("error", e.getMessage());
            builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
        }

        return builder.build();

    }



    @GET
    @Path("/library/options")
    @Produces("application/json")
    @Secured
    public Response getLibraryOptions() {
        Response.ResponseBuilder builder = null;

        try {

            List<Option> options = new ArrayList<>();
            for(Place library : placeService.getAll(Library.class)) {
                options.add(library.getOption());
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
    @Secured({Role.ADMIN, Role.MANAGER})
    @Produces("application/json")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response createPlace(@Context SecurityContext securityContext, Place place) {

        Response.ResponseBuilder builder;

        try {
            place.setId(null);
            placeService.addPlace(place);
            builder = Response.ok();

        } catch (Exception e) {
            Map<String, String> responseObj = new HashMap<>();
            responseObj.put("error", e.getMessage());
            builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
        }

        return builder.build();

    }

}
