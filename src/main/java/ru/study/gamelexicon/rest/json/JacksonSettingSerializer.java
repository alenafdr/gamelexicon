package ru.study.gamelexicon.rest.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import ru.study.gamelexicon.model.Setting;

import java.io.IOException;

public class JacksonSettingSerializer extends StdSerializer<Setting> {

    public JacksonSettingSerializer() {
        this(null);
    }

    public JacksonSettingSerializer(Class<Setting> t) {
        super(t);
    }

    @Override
    public void serialize(Setting setting, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        if (setting.getId() == null) {
            jsonGenerator.writeNullField("id");
        } else {
            jsonGenerator.writeNumberField("id", setting.getId());
        }

        jsonGenerator.writeObjectFieldStart("lang_question"); //lang_question
        jsonGenerator.writeNumberField("id", setting.getLangQuestion().getId());
        jsonGenerator.writeStringField("name", setting.getLangQuestion().getName());
        jsonGenerator.writeEndObject(); //lang_question

        jsonGenerator.writeObjectFieldStart("lang_answer"); //lang_answer
        jsonGenerator.writeNumberField("id", setting.getLangAnswer().getId());
        jsonGenerator.writeStringField("name", setting.getLangAnswer().getName());
        jsonGenerator.writeEndObject(); //lang_answer

        jsonGenerator.writeNumberField("amount_steps", setting.getAmountSteps());
        jsonGenerator.writeNumberField("sec_for_answer", setting.getSecForAnswer());
        jsonGenerator.writeNumberField("amount_repeat", setting.getAmountRepeat());

        jsonGenerator.writeEndObject();
    }
}
