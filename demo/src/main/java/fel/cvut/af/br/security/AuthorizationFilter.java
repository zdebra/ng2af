package fel.cvut.af.br.security;

import fel.cvut.af.br.model.Role;
import fel.cvut.af.br.model.User;

import javax.annotation.Priority;
import javax.naming.NoPermissionException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by zb on 12.1.16.
 * AUTHORIZATION filter is executed after AUTHENTICATION
 */
@Secured
@Provider
@Priority(Priorities.AUTHORIZATION)
public class AuthorizationFilter implements ContainerRequestFilter {

    @Context
    private ResourceInfo resourceInfo;

    @Context
    SecurityContext securityContext;

    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {

        Class<?> resourceClass = resourceInfo.getResourceClass();
        List<Role> classRoles = extractRoles(resourceClass);

        Method resourceMethod = resourceInfo.getResourceMethod();
        List<Role> methodRoles = extractRoles(resourceMethod);

        try {
            if(methodRoles.isEmpty()) {
                checkPermissions(classRoles);
            } else {
                checkPermissions(methodRoles);
            }
        } catch (NoPermissionException e) {
            containerRequestContext.abortWith(Response.status(Response.Status.FORBIDDEN).build());
        }

    }

    private List<Role> extractRoles(AnnotatedElement annotatedElement) {
        if (annotatedElement == null) {
            return new ArrayList<>();
        } else {
            Secured secured = annotatedElement.getAnnotation(Secured.class);
            if (secured == null) {
                return new ArrayList<>();
            } else {
                Role[] allowedRoles = secured.value();
                return Arrays.asList(allowedRoles);
            }
        }
    }

    private void checkPermissions(List<Role> allowedRoles) throws NoPermissionException {

        User user = (User) securityContext.getUserPrincipal();

        for(Role role : allowedRoles) {
            if(role.equals(user.getRole())) {
                return;
            }
        }
        throw new NoPermissionException("User " + user.getUsername() + " has no permission to do that.");

    }
}
