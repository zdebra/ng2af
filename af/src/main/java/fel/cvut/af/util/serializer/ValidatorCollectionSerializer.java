package fel.cvut.af.util.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import fel.cvut.af.model.validator.Validator;

import java.io.IOException;
import java.util.Collection;

/**
 * Serializes {@code Collection<Validator>}.
 */
public class ValidatorCollectionSerializer extends JsonSerializer<Collection<Validator>> {

    @Override
    public void serialize(Collection<Validator> validators, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {

        jsonGenerator.writeStartObject();

        for(Validator validator : validators) {

            jsonGenerator.writeObjectField(validator.getName(),validator);

        }

        jsonGenerator.writeEndObject();

    }
}
