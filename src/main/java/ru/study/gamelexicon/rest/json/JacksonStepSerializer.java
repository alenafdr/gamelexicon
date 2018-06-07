package ru.study.gamelexicon.rest.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import ru.study.gamelexicon.model.Step;
import ru.study.gamelexicon.model.Word;

import java.io.IOException;

public class JacksonStepSerializer extends StdSerializer<Step> {

    public JacksonStepSerializer() {
        this(null);
    }

    public JacksonStepSerializer(Class<Step> t) {
        super(t);
    }

    @Override
    public void serialize(Step step, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        if (step.getId() == null) {
            jsonGenerator.writeNullField("id");
        } else {
            jsonGenerator.writeNumberField("id", step.getId());
        }
            jsonGenerator.writeObjectFieldStart("wordQuestion"); //wordQuestion
            jsonGenerator.writeNumberField("id", step.getWordQuestion().getId());
            jsonGenerator.writeStringField("name", step.getWordQuestion().getName());
            jsonGenerator.writeEndObject(); //wordQuestion

        if (step.getWords() != null) {
            jsonGenerator.writeArrayFieldStart("options");// options
            for (Word word : step.getWords()){
                jsonGenerator.writeStartObject();
                jsonGenerator.writeNumberField("id", word.getId());
                jsonGenerator.writeStringField("name", word.getName());
                jsonGenerator.writeEndObject();
            }
            jsonGenerator.writeEndArray(); // options
        }
        jsonGenerator.writeBooleanField("result", step.isResultAnswer());
        jsonGenerator.writeEndObject();
    }
}
