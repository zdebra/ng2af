package fel.cvut.af.br.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import fel.cvut.af.br.model.Book;
import fel.cvut.af.br.service.AuthorService;
import fel.cvut.af.br.model.Author;
import fel.cvut.af.br.model.Role;
import fel.cvut.af.br.model.User;
import fel.cvut.af.br.security.Secured;
import fel.cvut.af.br.service.BookService;
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
 * Created by zb on 12.1.16.
 */
@Path("/book")
@Secured
@RequestScoped
public class BookEndpoint {

    @Inject
    private BookService bookService;

    @Inject
    private AuthorService authorService;

    @GET
    @Produces("application/json")
    public Response getAllBooks() {

        Response.ResponseBuilder builder = null;

        try {

            List<Book> books = bookService.getAll();

            ObjectMapper objectMapper = new ObjectMapper();
            StringBuilder sb = new StringBuilder();

            sb.append("[");
            int i = 0;
            for(Book b : books) {
                i++;
                sb.append(objectMapper.writeValueAsString(b));
                if(i<books.size()) {
                    sb.append(",");
                }
            }
            sb.append("]");

            builder = Response.ok().entity(sb.toString());

        } catch (Exception e) {
            Map<String, String> responseObj = new HashMap<>();
            responseObj.put("error", e.getMessage());
            builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
        }

        return builder.build();

    }

    @GET
    @Path("{id}")
    @Produces("application/json")
    public Response viewBook(@PathParam("id") Long id) {

        Response.ResponseBuilder builder = null;

        try {
            Book book = bookService.getBook(id);

            ObjectMapper objectMapper = new ObjectMapper();
            String jsonString = objectMapper.writeValueAsString(book);

            builder = Response.ok().entity(jsonString);

        } catch (Exception e) {
            Map<String, String> responseObj = new HashMap<>();
            responseObj.put("error", e.getMessage());
            builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
        }

        return builder.build();

    }

    @POST
    @Secured({Role.ADMIN, Role.EDITOR, Role.MANAGER})
    @Produces("application/json")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response createBook(@Context SecurityContext securityContext, Book book) {

        Response.ResponseBuilder builder = null;

        try {
            book.setId(null);
            bookService.addBook(book, (User) securityContext.getUserPrincipal());
            builder = Response.ok();

        } catch (Exception e) {
            Map<String, String> responseObj = new HashMap<>();
            responseObj.put("error", e.getMessage());
            builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
        }

        return builder.build();

    }

    @PUT
    @Path("{id}")
    @Secured({Role.ADMIN, Role.EDITOR, Role.MANAGER})
    @Produces("application/json")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response editBook(@Context SecurityContext securityContext, @PathParam("id") Long id, Book book) {

        Response.ResponseBuilder builder = null;

        try {
            bookService.editBook(id, book, (User) securityContext.getUserPrincipal());
            builder = Response.ok();

        } catch (Exception e) {
            e.printStackTrace();
            Map<String, String> responseObj = new HashMap<>();
            responseObj.put("error", e.getMessage());
            builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
        }

        return builder.build();

    }


    @GET
    @Path("/author/options")
    @Produces("application/json")
    @Secured
    public Response getAuthorOptions() {
        Response.ResponseBuilder builder = null;

        try {

            List<Option> options = new ArrayList<>();
            for(Author author : authorService.getAll()) {
                options.add(author.getOption());
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
    @Path("/author")
    @Produces("application/json")
    @Secured
    public Response getAuthors() {
        Response.ResponseBuilder builder = null;

        try {
            builder = Response.ok().entity(authorService.getAll());

        } catch (Exception e) {
            Map<String, String> responseObj = new HashMap<>();
            responseObj.put("error", e.getMessage());
            builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
        }

        return builder.build();

    }




}
