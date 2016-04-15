package fel.cvut.af.util.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import fel.cvut.af.model.validator.IntegerValueValidator;

import java.io.IOException;

/**
 * Serializes {@code IntegerValueValidator}.
 */
public class IntegerValueValidatorSerializer extends ValidatorSerializer<IntegerValueValidator> {

    @Override
    public void serializeInside(IntegerValueValidator validator, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeNumberField("value",validator.getValue());
    }
}
