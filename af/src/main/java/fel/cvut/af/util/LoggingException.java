package fel.cvut.af.util;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Encapsulating exception with additional logging info.
 */
public class LoggingException extends Exception {

    /**
     *
     * @param message to be logged
     * @param logger where to log
     */
    public LoggingException(String message, Logger logger) {
        super(message);
        logger.log(Level.WARNING, message);
    }
}
