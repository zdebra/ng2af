package fel.cvut.af.br.security;

import fel.cvut.af.br.model.Role;

import javax.ws.rs.NameBinding;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by zb on 11.1.16.
 */
@NameBinding
@Retention(RUNTIME)
@Target({TYPE,METHOD})
public @interface Secured {
    Role[] value() default {Role.ADMIN, Role.EDITOR, Role.VISITOR, Role.MANAGER};
}
