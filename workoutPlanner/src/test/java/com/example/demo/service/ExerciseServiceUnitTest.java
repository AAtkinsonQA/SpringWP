package com.example.demo.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.DayOfWeek;
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
		Exercise newExercise = new Exercise(DayOfWeek.MONDAY, "run", 30L, 8L);
		Exercise savedExercise = new Exercise(DayOfWeek.MONDAY, "run", 30L, 8L);
		savedExercise.setId(id);

		Mockito.when(this.repo.save(newExercise)).thenReturn(savedExercise);
		assertThat(this.service.createExercise(newExercise)).isEqualTo(savedExercise);

	}

	@Test
	void testUpdate() {

		Long id = 1L;
		Exercise newExercise = new Exercise(DayOfWeek.MONDAY, "run", 30L, 8L);
		Exercise oldExercise = new Exercise(DayOfWeek.MONDAY, "cycling", 60L, 8L);
		oldExercise.setId(id);
		Exercise updatedExercise = new Exercise(DayOfWeek.MONDAY, "run", 30L, 8L);
		updatedExercise.setId(id);

		Mockito.when(this.repo.findById(id)).thenReturn(Optional.of(oldExercise));
		Mockito.when(this.repo.save(updatedExercise)).thenReturn(updatedExercise);

		assertThat(this.service.updateExercise(newExercise, id)).isEqualTo(updatedExercise);

	}

	@Test
	void testGet() {

		List<Exercise> exercises = new ArrayList<>();
		Long id = 1L;
		Exercise newExercise = new Exercise(DayOfWeek.MONDAY, "run", 30L, 8L);
		newExercise.setId(id);
		exercises.add(newExercise);

		Mockito.when(this.repo.findAll()).thenReturn(exercises);

		assertThat(this.service.getExercise()).isEqualTo(exercises);
	}

	@Test
	void testDelete() {

		Long id = 1L;
		Exercise exerciseToRemove = new Exercise(DayOfWeek.MONDAY, "run", 30L, 8L);
		exerciseToRemove.setId(id);

		Mockito.when(this.repo.existsById(id)).thenReturn(false);

		assertThat(this.service.deleteExercise(id)).isEqualTo(true);

	}

}
