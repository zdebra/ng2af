package fel.cvut.af.br.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import fel.cvut.af.model.options.*;
import fel.cvut.af.util.serializer.BooleanOptionSerializer;
import fel.cvut.af.util.serializer.EntityOptionSerializer;
import fel.cvut.af.util.serializer.IntegerOptionSerializer;
import fel.cvut.af.util.serializer.StringOptionSerializer;


import java.util.List;

/**
 * Created by Zdeněk Brabec
 * brabezd1@fel.cvut.cz
 * 17.3.16
 */
public class JsonUtil {

    public static String toJsonArray(List<?> collection) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        StringBuilder sb = new StringBuilder();

        sb.append("[");
        for(int i = 0; i < collection.size(); i++) {
            sb.append(objectMapper.writeValueAsString(collection.get(i)));
            if(i<collection.size()-1) {
                sb.append(",");
            }
        }
        sb.append("]");

        return sb.toString();

    }

    public static String optionsToJsonObject(List<Option> collection) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(BooleanOption.class, new BooleanOptionSerializer());
        simpleModule.addSerializer(NumberOption.class, new IntegerOptionSerializer());
        simpleModule.addSerializer(StringOption.class, new StringOptionSerializer());
        simpleModule.addSerializer(EntityOption.class, new EntityOptionSerializer());
        objectMapper.registerModule(simpleModule);


        StringBuilder sb = new StringBuilder();

        sb.append("{");
        for(int i = 0; i < collection.size(); i++) {
            sb.append("\"");
            sb.append(collection.get(i).getName());
            sb.append("\":");
            sb.append(objectMapper.writeValueAsString(collection.get(i)));
            if(i<collection.size()-1) {
                sb.append(",");
            }
        }
        sb.append("}");

        return sb.toString();


    }

}
