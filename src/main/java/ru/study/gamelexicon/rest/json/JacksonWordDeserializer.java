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
import ru.study.gamelexicon.model.Language;
import ru.study.gamelexicon.model.Word;

import java.io.IOException;


public class JacksonWordDeserializer extends StdDeserializer<Word> {

	public JacksonWordDeserializer(){
		this(null);
	}

	public JacksonWordDeserializer(Class<Word> t) {
		super(t);
	}

	@Override
	public Word deserialize(JsonParser parser, DeserializationContext context) throws IOException, JsonProcessingException {
		JsonNode node = parser.getCodec().readTree(parser);
		Word word = new Word();
		JsonNode language_node = node.get("language");

		Language language = new Language();
		language.setId(language_node.get("id").asLong());
		language.setName(language_node.get("name").asText());

		word.setId(node.get("id").asLong());
		word.setName(node.get("name").asText());
		word.setLang(language);
		return word;
	}

}
