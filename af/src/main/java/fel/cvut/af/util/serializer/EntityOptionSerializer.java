package fel.cvut.af.util.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import fel.cvut.af.model.options.EntityOption;

import java.io.IOException;

/**
 * Serializes {@code EntityOption}.
 * <p>
 * Example of serialization product:
 * {name: "option name", label: "option label", value: 5, @class: "entity"}
 */
public class EntityOptionSerializer extends JsonSerializer<EntityOption> {

    @Override
    public void serializeWithType(EntityOption entityOption, JsonGenerator jsonGenerator, SerializerProvider serializers, TypeSerializer typeSer) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("name", entityOption.getName());
        jsonGenerator.writeStringField("label", entityOption.getLabel());
        jsonGenerator.writeNumberField("value", entityOption.getValue());
        jsonGenerator.writeStringField(typeSer.getPropertyName(),"entity");
        jsonGenerator.writeEndObject();
    }

    @Override
    public void serialize(EntityOption entityOption, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("name", entityOption.getName());
        jsonGenerator.writeStringField("label", entityOption.getLabel());
        jsonGenerator.writeNumberField("value", entityOption.getValue());
        jsonGenerator.writeEndObject();
    }
}
