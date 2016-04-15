package fel.cvut.af.util.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import fel.cvut.af.model.options.BooleanOption;

import java.io.IOException;

/**
 * Serializes {@code BooleanOption}.
 * <p>
 * Serialization example:
 * {name: "option name", label: "option label", value: true}
 */
public class BooleanOptionSerializer extends JsonSerializer<BooleanOption> {
    @Override
    public void serialize(BooleanOption booleanOption, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("name", booleanOption.getName());
        jsonGenerator.writeStringField("label", booleanOption.getLabel());
        jsonGenerator.writeBooleanField("value", booleanOption.getValue());
        jsonGenerator.writeEndObject();
    }
}
