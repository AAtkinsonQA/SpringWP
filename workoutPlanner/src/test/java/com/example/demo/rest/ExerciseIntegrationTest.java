package com.example.demo.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;

import com.example.demo.persistance.domain.Exercise;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Sql(scripts = { "classpath:exercise-schema.sql",
		"classpath:exercise-data.sql" }, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
public class ExerciseIntegrationTest {

	@Autowired
	private MockMvc mockMVC;

	@Autowired
	private ObjectMapper mapper;

	@Test
	void testCreate() throws Exception {
		Exercise newExercise = new Exercise("running", 45L);
		String requestBody = this.mapper.writeValueAsString(newExercise);
		RequestBuilder req = post("/create").contentType(MediaType.APPLICATION_JSON).content(requestBody);

		ResultMatcher checkStatus = status().isCreated();

		Exercise savedExercise = new Exercise("running", 45);
		savedExercise.setId(2L);

		String resultBody = this.mapper.writeValueAsString(savedExercise);
		ResultMatcher checkBody = content().json(resultBody);

		this.mockMVC.perform(req).andExpect(checkStatus).andExpect(checkBody);

		MvcResult result = this.mockMVC.perform(req).andExpect(checkStatus).andReturn();

		String reqBody = result.getResponse().getContentAsString();

		Exercise exerciseResult = this.mapper.readValue(reqBody, Exercise.class);

	}

}
