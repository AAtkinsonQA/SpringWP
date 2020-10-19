package com.example.demo.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
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
@ActiveProfiles(profiles = "test")
public class ExerciseIntegrationTest {

	@Autowired
	private MockMvc mockMVC;

	@Autowired
	private ObjectMapper mapper;

	@Test
	void testCreate() throws Exception {
		Exercise newExercise = new Exercise(DayOfWeek.MONDAY, "running", 45L, 8L, false);
		String requestBody = this.mapper.writeValueAsString(newExercise);
		RequestBuilder req = post("/create").contentType(MediaType.APPLICATION_JSON).content(requestBody);

		ResultMatcher checkStatus = status().isCreated();

		Exercise savedExercise = new Exercise(DayOfWeek.MONDAY, "running", 45L, 8L, false);
		savedExercise.setId(2L);

		String resultBody = this.mapper.writeValueAsString(savedExercise);
		ResultMatcher checkBody = content().json(resultBody);

		this.mockMVC.perform(req).andExpect(checkStatus).andExpect(checkBody);

		MvcResult result = this.mockMVC.perform(req).andExpect(checkStatus).andReturn();

		String reqBody = result.getResponse().getContentAsString();

		Exercise exerciseResult = this.mapper.readValue(reqBody, Exercise.class);

	}

	@Test
	void testUpdate() throws Exception {
		Exercise newExercise = new Exercise(DayOfWeek.MONDAY, "run", 30L, 1, false);
		String requestBody = this.mapper.writeValueAsString(newExercise);
		RequestBuilder request = put("/update?id=1").contentType(MediaType.APPLICATION_JSON).content(requestBody);

		ResultMatcher checkStatus = status().isAccepted();

		Exercise savedExercise = new Exercise(DayOfWeek.MONDAY, "run", 30L, 1, false);
		savedExercise.setId(1L);

		String resultBody = this.mapper.writeValueAsString(savedExercise);
		ResultMatcher checkBody = content().json(resultBody);

		this.mockMVC.perform(request).andExpect(checkStatus).andExpect(checkBody);
	}

	@Test
	void testDelete() throws Exception {
		RequestBuilder request = delete("/remove/1");

		ResultMatcher checkStatus = status().is(200);

		this.mockMVC.perform(request).andExpect(checkStatus);

//		RequestBuilder request2 = delete("/remove/4");
//
//		ResultMatcher checkStatus2 = status().is(500);
//
//		this.mockMVC.perform(request2).andExpect(checkStatus2);
	}

	@Test
	void testRead() throws Exception {
		Exercise exercise = new Exercise(DayOfWeek.MONDAY, "run", 30L, 8L, false);
		exercise.setId(1L); // wood object to match the one in wood-data.sql
		List<Exercise> exercises = new ArrayList<>();
		exercises.add(exercise);
		String responseBody = this.mapper.writeValueAsString(exercises);

		this.mockMVC.perform(get("/get")).andExpect(status().isOk()).andExpect(content().json(responseBody));
	}

}
