package utility;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {
    public String toJSON(){
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(this);
        }catch (Exception e){
            return "";
        }
    }
}
