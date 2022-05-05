package davidgoldstein.blackjack.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.Test;

public class HandTest {

    @Test
    void isSerializable() throws JsonMappingException, JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Hand h = new Hand();
        mapper.writeValueAsString(h);
    }
}
