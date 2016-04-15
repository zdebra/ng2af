package fel.cvut.af.br.util.deserializator;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import fel.cvut.af.br.model.Author;

import java.io.IOException;

/**
 * Created by Zdeněk Brabec
 * brabezd1@fel.cvut.cz
 * 21.3.16
 */
public class BookAuthorDeserializer extends JsonDeserializer<Author> {
    @Override
    public Author deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        String value = jsonParser.getValueAsString();
        if(value==null) {
            return null;
        }
        return new Author(value);
    }
}
