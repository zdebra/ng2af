package fel.cvut.af.br.util.deserializator;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import fel.cvut.af.br.model.Library;
import fel.cvut.af.br.model.Place;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Zdeněk Brabec
 * brabezd1@fel.cvut.cz
 * 21.3.16
 */
public class BookLibraryCollectionDeserializer extends JsonDeserializer<Collection<Place>> {

    @Override
    public Collection<Place> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {

        Collection<Place> stores = new ArrayList<>();

        JsonToken token;
        while((token=jsonParser.nextToken()) != JsonToken.END_OBJECT) {
            if (token == JsonToken.VALUE_STRING) {
                String fieldname = jsonParser.getCurrentName();
                String value = jsonParser.getValueAsString();

                stores.add(new Library(value, null));
            }
        }

        return stores;

    }
}
