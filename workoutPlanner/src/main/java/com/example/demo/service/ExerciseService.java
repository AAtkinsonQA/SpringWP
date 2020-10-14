package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.persistance.domain.Exercise;
import com.example.demo.persistance.domain.ExerciseRepo;

@Service
public class ExerciseService {
	private ExerciseRepo repo;

	public ExerciseService(ExerciseRepo repo) {
		super();
		this.repo = repo;
	}

	public Exercise createExercise(Exercise exercise) {
		return this.repo.save(exercise);
	}

	public List<Exercise> getExercise() {
		return this.repo.findAll();
	}

	public boolean deleteExercise(long id) {
		this.repo.deleteById(id);
		return !this.repo.existsById(id);
	}

	public Exercise updateExercise(Exercise exercise, long id) {
		Exercise oldExercise = this.repo.findById(id).get();
		oldExercise.setName(exercise.getName());
		oldExercise.setTime(exercise.getTime());
		return this.repo.save(oldExercise);
	}

	public List<Exercise> getExerciseByName(String name) {
		return this.repo.findByName(name);
	}

}
