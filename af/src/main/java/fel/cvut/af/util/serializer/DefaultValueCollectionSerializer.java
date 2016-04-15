package fel.cvut.af.util.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import fel.cvut.af.model.options.*;

import java.io.IOException;
import java.util.Collection;

/**
 * Serializes {@code Collection<Option>} for default values of input element.
 * <p>
 * Example of serialization product:
 * {optionName1: true, optionName2: "string value", optionName3: 5}
 */
public class DefaultValueCollectionSerializer extends JsonSerializer<Collection<Option>> {
    @Override
    public void serialize(Collection<Option> options, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {

        jsonGenerator.writeStartObject();

        for (Option option : options) {
            if(option.getClass().equals(BooleanOption.class)) {
                    jsonGenerator.writeBooleanField(option.getName(),(Boolean) option.getValue());
            }

            if(option.getClass().equals(StringOption.class)) {
                jsonGenerator.writeStringField(option.getName(),(String) option.getValue());
            }

            if(option.getClass().equals(NumberOption.class)) {
                jsonGenerator.writeNumberField(option.getName(),(Integer) option.getValue());
            }

            if(option.getClass().equals(EntityOption.class)) {
                jsonGenerator.writeNumberField(option.getName(),(Long) option.getValue());
            }

        }
        jsonGenerator.writeEndObject();

    }
}
