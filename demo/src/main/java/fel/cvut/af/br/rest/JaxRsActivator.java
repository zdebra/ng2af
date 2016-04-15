package fel.cvut.af.br.rest;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * A class extending {@link Application} and annotated with @ApplicationPath is the Java EE 7 "no XML" approach to activating
 * JAX-RS.
 * 
 * <p>
 * ResourcesProducers are served relative to the servlet path specified in the {@link ApplicationPath} annotation.
 * </p>
 */
@ApplicationPath("/rest")
public class JaxRsActivator extends Application {
    /* class body intentionally left blank */
}
