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
import ru.study.gamelexicon.model.Setting;

import java.io.IOException;


public class JacksonSettingDeserializer extends StdDeserializer<Setting> {

	public JacksonSettingDeserializer(){
		this(null);
	}

	public JacksonSettingDeserializer(Class<Setting> t) {
		super(t);
	}

	@Override
	public Setting deserialize(JsonParser parser, DeserializationContext context) throws IOException, JsonProcessingException {
		JsonNode node = parser.getCodec().readTree(parser);
		Setting setting = new Setting();
		JsonNode lang_question_node = node.get("lang_question");
		JsonNode lang_answer_node = node.get("lang_answer");

		Language langQuestion = new Language();
		Language langAnswer = new Language();

		langQuestion.setId(lang_question_node.get("id").asLong());
		langQuestion.setName(lang_question_node.get("name").asText());

		langAnswer.setId(lang_answer_node.get("id").asLong());
		langAnswer.setName(lang_answer_node.get("name").asText());

		setting.setId(node.get("id").asLong());
		setting.setLangQuestion(langQuestion);
		setting.setLangAnswer(langAnswer);
		setting.setAmountSteps(node.get("amount_steps").asInt());
		setting.setSecForAnswer(node.get("sec_for_answer").asInt());
		setting.setAmountRepeat(node.get("amount_repeat").asInt());

		return setting;
	}

}
