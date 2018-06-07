package ru.study.gamelexicon.rest.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import ru.study.gamelexicon.model.Game;
import ru.study.gamelexicon.model.Step;

import java.io.IOException;

public class JacksonGameSerializer extends StdSerializer<Game> {

    public JacksonGameSerializer() {
        this(null);
    }

    public JacksonGameSerializer(Class<Game> t) {
        super(t);
    }

    @Override
    public void serialize(Game game, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        if (game.getId() == null) {
            jsonGenerator.writeNullField("gameId");
        } else {
            jsonGenerator.writeNumberField("gameId", game.getId());
        }
        jsonGenerator.writeStringField("language question", game.getLangWordQuestion().getName());
        jsonGenerator.writeStringField("language answer", game.getLangWordAnswer().getName());

        jsonGenerator.writeArrayFieldStart("steps");
        for (Step step : game.getSteps()){
            jsonGenerator.writeStartObject(); // step
            if (step.getId() == null) {
                jsonGenerator.writeNullField("stepId");
            } else {
                jsonGenerator.writeNumberField("stepId", step.getId());
            }
            jsonGenerator.writeBooleanField("result", step.isResultAnswer());
            jsonGenerator.writeEndObject(); // step
        }

        jsonGenerator.writeEndArray(); // steps

        jsonGenerator.writeEndObject();
    }
}
