package fel.cvut.af.util.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import fel.cvut.af.model.options.Option;

import java.io.IOException;
import java.util.Collection;

/**
 * Serializes {@code Collection<Option>}.
 */
public class OptionCollectionSerializer extends JsonSerializer<Collection<Option>> {

    @Override
    public void serialize(Collection<Option> options, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {

        jsonGenerator.writeStartObject();

        for(Option option : options) {
            jsonGenerator.writeObjectField(option.getName(),option);
        }

        jsonGenerator.writeEndObject();

    }
}
