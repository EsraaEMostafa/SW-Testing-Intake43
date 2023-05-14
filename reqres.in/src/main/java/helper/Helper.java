package helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.mapper.ObjectMapperDeserializationContext;
import io.restassured.mapper.ObjectMapperSerializationContext;

public class Helper {
    //function take java object and return string to use in (Body request)
    public  static String getObjectAsString (Object object)
    {

        ObjectMapper objectMapper = new ObjectMapper() ;
        String payload ;
        try {
             payload = objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return  payload ;

    }
}
