package fel.cvut.af.util.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import fel.cvut.af.model.options.StringOption;

import java.io.IOException;

/**
 * Serializes {@code StringOption}.
 * <p>
 * Serialization product example:
 * {name: "option name", label: "option label", value: "string value"}
 */
public class StringOptionSerializer extends JsonSerializer<StringOption> {
    @Override
    public void serialize(StringOption option, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("name", option.getName());
        jsonGenerator.writeStringField("label", option.getLabel());
        jsonGenerator.writeStringField("value", option.getValue());
        jsonGenerator.writeEndObject();
    }
}
