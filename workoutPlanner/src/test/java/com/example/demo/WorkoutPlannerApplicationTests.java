package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.example.demo.persistance.domain.Exercise;

import nl.jqno.equalsverifier.EqualsVerifier;

@SpringBootTest
@ActiveProfiles(profiles = "test")
class WorkoutPlannerApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void verifyEquals() {
		EqualsVerifier.forClass(Exercise.class).usingGetClass().verify();
	}

}
