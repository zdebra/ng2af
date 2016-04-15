package fel.cvut.af.util.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import fel.cvut.af.model.options.Option;

import java.io.IOException;

/**
 * Serializes {@code Option} as a default value of input element.
 */
public class DefaultValueSerializer extends JsonSerializer<Option> {

    @Override
    public void serializeWithType(Option option, JsonGenerator jsonGenerator, SerializerProvider serializers, TypeSerializer typeSer) throws IOException {
        jsonGenerator.writeString(option.getName());
    }

    @Override
    public void serialize(Option option, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(option.getName());
    }
}
