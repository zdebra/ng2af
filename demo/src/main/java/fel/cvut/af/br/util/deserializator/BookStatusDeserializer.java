package fel.cvut.af.br.util.deserializator;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

/**
 * Created by Zdeněk Brabec
 * brabezd1@fel.cvut.cz
 * 21.3.16
 */
public class BookStatusDeserializer extends JsonDeserializer<Boolean> {
    @Override
    public Boolean deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        String strVal = jsonParser.getValueAsString();
        return strVal.equals("active");
    }
}
