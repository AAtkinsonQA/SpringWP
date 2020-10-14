package com.example.demo.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.example.demo.persistance.domain.Exercise;
import com.example.demo.persistance.domain.ExerciseRepo;

@SpringBootTest
public class ExerciseServiceUnitTest {

	@Autowired
	private ExerciseService service;

	@MockBean
	private ExerciseRepo repo;

	@Test
	void testCreate() {

		Long id = 1L;
		Exercise newExercise = new Exercise("run", 30L);
		Exercise savedExercise = new Exercise("run", 30L);
		savedExercise.setId(id);

		Mockito.when(this.repo.save(newExercise)).thenReturn(savedExercise);
		assertThat(this.service.createExercise(newExercise)).isEqualTo(savedExercise);

	}

	@Test
	void testUpdate() {

		Long id = 1L;
		Exercise newExercise = new Exercise("run", 30L);
		Exercise oldExercise = new Exercise("cycling", 60L);
		oldExercise.setId(id);
		Exercise updatedExercise = new Exercise("run", 30L);
		updatedExercise.setId(id);

		Mockito.when(this.repo.findById(id)).thenReturn(Optional.of(oldExercise));
		Mockito.when(this.repo.save(updatedExercise)).thenReturn(updatedExercise);

		assertThat(this.service.updateExercise(newExercise, id)).isEqualTo(updatedExercise);

	}

	@Test
	void testGet() {

		List<Exercise> exercises = new ArrayList<>();
		Long id = 1L;
		Exercise newExercise = new Exercise("run", 30L);
		newExercise.setId(id);
		exercises.add(newExercise);

		Mockito.when(this.repo.findAll()).thenReturn(exercises);

		assertThat(this.service.getExercise()).isEqualTo(exercises);
	}

	@Test
	void testDelete() {

		Long id = 1L;
		Exercise exerciseToRemove = new Exercise("run", 30L);
		exerciseToRemove.setId(id);

		Mockito.when(this.repo.existsById(id)).thenReturn(false);

		assertThat(this.service.deleteExercise(id)).isEqualTo(true);

	}

}
