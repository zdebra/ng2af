package fel.cvut.af.util.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import fel.cvut.af.model.validator.StringValueValidator;

import java.io.IOException;

/**
 * Serializes {@code StringValueValidator}.
 */
public class StringValueValidatorSerializer extends ValidatorSerializer<StringValueValidator> {
    @Override
    public void serializeInside(StringValueValidator validator, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStringField("value", validator.getValue());
    }
}
