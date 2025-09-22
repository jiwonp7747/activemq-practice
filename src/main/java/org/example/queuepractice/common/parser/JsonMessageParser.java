package org.example.queuepractice.common.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JsonMessageParser {

    private final ObjectMapper mapper;

    public String parseMessageType(String message) {

        try {
            JsonNode json = mapper.readTree(message);
            String type = json.get("type").asText();

            return type;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }
}
