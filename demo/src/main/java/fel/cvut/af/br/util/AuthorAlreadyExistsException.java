package fel.cvut.af.br.util;

import fel.cvut.af.br.model.Author;
import fel.cvut.af.util.LoggingException;

import java.util.logging.Logger;

/**
 * Created by zdebra on 22.2.16.
 */
public class AuthorAlreadyExistsException extends LoggingException {

    public AuthorAlreadyExistsException(Author author, Logger logger) {
        super("Author "+author.getName()+" already exists!", logger);
    }
}
