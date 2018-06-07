package ru.study.gamelexicon.rest.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import ru.study.gamelexicon.model.Role;
import ru.study.gamelexicon.model.User;

import java.io.IOException;

public class JacksonUserSerializer extends StdSerializer<User> {

    public JacksonUserSerializer() {
        this(null);
    }

    public JacksonUserSerializer(Class<User> t) {
        super(t);
    }

    @Override
    public void serialize(User user, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        if (user.getId() == null) {
            jsonGenerator.writeNullField("id");
        } else {
            jsonGenerator.writeNumberField("id", user.getId());
        }

        jsonGenerator.writeStringField("email", user.getEmail());
        jsonGenerator.writeStringField("first_name", user.getFirstName());
        jsonGenerator.writeStringField("last_name", user.getLastName());
        jsonGenerator.writeArrayFieldStart("roles");//roles
        for (Role role : user.getRoles()){
            jsonGenerator.writeStartObject();
            jsonGenerator.writeNumberField("id", role.getId());
            jsonGenerator.writeStringField("name", role.getName());
            jsonGenerator.writeEndObject();
        }
        jsonGenerator.writeEndArray();//roles
        if (user.getId() == null) {
            jsonGenerator.writeNullField("token");
        } else {
            jsonGenerator.writeStringField("token", user.getToken());
        }
        jsonGenerator.writeNumberField("setting_id", user.getSetting().getId());
        jsonGenerator.writeEndObject();
    }
}
