package fel.cvut.af.util.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import fel.cvut.af.model.validator.BooleanValueValidator;

import java.io.IOException;

/**
 * Serializes {@code BooleanValueValidator}.
 */
public class BooleanValueValidatorSerializer extends ValidatorSerializer<BooleanValueValidator> {
    @Override
    public void serializeInside(BooleanValueValidator validator, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeBooleanField("value", validator.getValue());
    }
}
