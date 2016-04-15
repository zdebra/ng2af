package fel.cvut.af.util.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import fel.cvut.af.model.options.NumberOption;

import java.io.IOException;

/**
 * Serializes {@code NumberOption}.
 * <p>
 * Example of serialization product:
 * {name: "option name", label: "option label", value: 5}
 */
public class IntegerOptionSerializer extends JsonSerializer<NumberOption> {
    @Override
    public void serialize(NumberOption integerOption, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("name", integerOption.getName());
        jsonGenerator.writeStringField("label", integerOption.getLabel());
        jsonGenerator.writeNumberField("value", integerOption.getValue());
        jsonGenerator.writeEndObject();
    }
}
