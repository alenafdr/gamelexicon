package ru.study.gamelexicon.rest.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import ru.study.gamelexicon.model.Language;

import java.io.IOException;

public class JacksonLanguageSerializer extends StdSerializer<Language> {

    public JacksonLanguageSerializer() {
        this(null);
    }

    public JacksonLanguageSerializer(Class<Language> t) {
        super(t);
    }

    @Override
    public void serialize(Language language, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        if (language.getId() == null) {
            jsonGenerator.writeNullField("id");
        } else {
            jsonGenerator.writeNumberField("id", language.getId());
        }

        jsonGenerator.writeStringField("name", language.getName());

        jsonGenerator.writeEndObject();
    }
}
