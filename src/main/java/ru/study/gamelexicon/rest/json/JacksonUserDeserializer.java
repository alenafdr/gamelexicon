/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ru.study.gamelexicon.rest.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import ru.study.gamelexicon.model.Role;
import ru.study.gamelexicon.model.User;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


public class JacksonUserDeserializer extends StdDeserializer<User> {

	public JacksonUserDeserializer(){
		this(null);
	}

	public JacksonUserDeserializer(Class<User> t) {
		super(t);
	}

	@Override
	public User deserialize(JsonParser parser, DeserializationContext context) throws IOException, JsonProcessingException {
		JsonNode node = parser.getCodec().readTree(parser);
		User user = new User();
		Set<Role> roles = new HashSet<>();
		JsonNode roles_node = node.get("roles");
		Iterator<JsonNode> iterator = roles_node.iterator();
		while (iterator.hasNext()) {
			JsonNode jsonNodeIterator = iterator.next();
			Role role = new Role();
			role.setId(jsonNodeIterator.get("id").asLong());
			role.setName(jsonNodeIterator.get("name").asText());
			roles.add(role);
		}

		user.setId(node.get("id").asLong());
		user.setEmail(node.get("email").asText());
		user.setFirstName(node.get("first_name").asText());
		user.setLastName(node.get("last_name").asText());
		user.setRoles(roles);
		return user;
	}

}
