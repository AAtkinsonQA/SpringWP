package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.persistance.domain.Exercise;

import nl.jqno.equalsverifier.EqualsVerifier;

@SpringBootTest
class WorkoutPlannerApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void verifyEquals() {
		EqualsVerifier.forClass(Exercise.class).usingGetClass().verify();
	}

}
