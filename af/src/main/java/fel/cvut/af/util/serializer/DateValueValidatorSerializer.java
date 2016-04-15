package fel.cvut.af.util.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import fel.cvut.af.model.validator.DateValueValidator;

import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * Serialize {@code DateValueValidator}.
 */
public class DateValueValidatorSerializer extends ValidatorSerializer<DateValueValidator> {
    @Override
    public void serializeInside(DateValueValidator validator, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        jsonGenerator.writeStringField("value", simpleDateFormat.format(validator.getValue()));
    }
}
