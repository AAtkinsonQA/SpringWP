package com.example.demo.rest;

import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.persistance.domain.Exercise;
import com.example.demo.service.ExerciseService;

@CrossOrigin
@RestController
public class Controller {

	private ExerciseService service;

	public Controller(ExerciseService service) {
		super();
		this.service = service;
	}

	@GetMapping("/get")
	public ResponseEntity<List<Exercise>> getExercise() {
		return ResponseEntity.ok(this.service.getExercise());
	}

//	@GetMapping("/name/{name}")
//	public List<Exercise> getExerciseByName(@PathVariable String name) {
//		return this.service.getDuckByColour(colour);
//	}

	@PostMapping("/create")
	public ResponseEntity<Exercise> createExercise(@RequestBody Exercise exercise) {
		return new ResponseEntity<Exercise>(this.service.createExercise(exercise), HttpStatus.CREATED);
	}

	@DeleteMapping("/remove/{id}")
	public ResponseEntity<Object> deleteExercise(@PathVariable Long id) {
		if (this.service.deleteExercise(id)) {
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/update")
	public ResponseEntity<Exercise> updateExercise(@RequestBody Exercise exercise, @PathParam("id") long id) {
		return new ResponseEntity<Exercise>(this.service.updateExercise(exercise, id), HttpStatus.ACCEPTED);
	}
}
