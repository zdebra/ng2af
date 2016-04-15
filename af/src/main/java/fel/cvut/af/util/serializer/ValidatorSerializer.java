package fel.cvut.af.util.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import fel.cvut.af.model.validator.Validator;

import java.io.IOException;

/**
 * An abstract predecessor of {@code Validator} serializers.
 *
 * @param <T> the validator type to be serialized
 */
public abstract class ValidatorSerializer<T extends Validator> extends JsonSerializer<T> {

    /**
     * Serialize inside of json object.
     *
     * @param validator to be serialized
     * @param jsonGenerator the provided generator
     * @param serializerProvider the provider
     * @throws IOException when serialization fails
     */
    public abstract void serializeInside(T validator, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException;

    @Override
    public void serialize(T validator, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();

        serializeInside(validator,jsonGenerator,serializerProvider);

        jsonGenerator.writeStringField("message", validator.getMessage());
        jsonGenerator.writeEndObject();
    }
}
