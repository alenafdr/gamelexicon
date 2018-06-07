package ru.study.gamelexicon.rest.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import ru.study.gamelexicon.model.Word;

import java.io.IOException;

public class JacksonWordSerializer extends StdSerializer<Word> {

    public JacksonWordSerializer() {
        this(null);
    }

    public JacksonWordSerializer(Class<Word> t) {
        super(t);
    }

    @Override
    public void serialize(Word word, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        if (word.getId() == null) {
            jsonGenerator.writeNullField("id");
        } else {
            jsonGenerator.writeNumberField("id", word.getId());
        }

        jsonGenerator.writeStringField("name", word.getName());
        jsonGenerator.writeObjectFieldStart("language"); //language
        jsonGenerator.writeNumberField("id", word.getLang().getId());
        jsonGenerator.writeStringField("name", word.getLang().getName());
        jsonGenerator.writeEndObject(); //language

        jsonGenerator.writeEndObject();
    }
}
