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
import ru.study.gamelexicon.model.Game;
import ru.study.gamelexicon.model.Step;
import ru.study.gamelexicon.model.Word;

import java.io.IOException;


public class JacksonStepDeserializer extends StdDeserializer<Step> {

	public JacksonStepDeserializer(){
		this(null);
	}

	public JacksonStepDeserializer(Class<Step> t) {
		super(t);
	}

	@Override
	public Step deserialize(JsonParser parser, DeserializationContext context) throws IOException, JsonProcessingException {
		JsonNode node = parser.getCodec().readTree(parser);
		JsonNode word_question_node = node.get("wordQuestion");
		JsonNode word_answer_node = node.get("wordAnswer");
		Long id = node.get("id").asLong();
		Step step = new Step();
		Word wordQuestion = new Word();
		Word wordAnswer = new Word();

		wordQuestion.setId(word_question_node.get("id").asLong());
		wordQuestion.setName(word_question_node.get("name").asText());

		wordAnswer.setId(word_answer_node.get("id").asLong());
		wordAnswer.setName(word_answer_node.get("name").asText());

		step.setId(id);
		step.setWordQuestion(wordQuestion);
		step.setAnswer(wordAnswer);
		step.setGame(new Game());
		return step;
	}

}
