package com.vectoscalar.springboot.assignment.controller;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vectoscalar.springboot.assignment.entity.Student;
import com.vectoscalar.springboot.assignment.request.StudentRequest;
import com.vectoscalar.springboot.assignment.response.StudentResponse;
import com.vectoscalar.springboot.assignment.service.StudentService;

@RestController
@RequestMapping("/students")
public class StudentController {

	@Autowired
	private StudentService studentService;
	
	public StudentController() {
		
	}
	
	// expose "/students" and return list of students
	
	@GetMapping("")
	public ResponseEntity<List<StudentResponse>> findAll() {
		List<Student> students = studentService.findAll();
		List<StudentResponse> studentResponse = new ObjectMapper().convertValue(students,  new TypeReference<List<StudentResponse>>() {});
		return new ResponseEntity<>(studentResponse, HttpStatus.OK);
	}

	// add mapping for GET /students/{studentId}
	
	@GetMapping("/{studentId}")
	public ResponseEntity<StudentResponse> getStudent(@PathVariable Integer studentId){
		Student student = studentService.findById(studentId);
		
		if (student == null) {
			throw new EntityNotFoundException("Student id not found - " + studentId);
		}
		StudentResponse studentResponse = new ObjectMapper().convertValue(student, StudentResponse.class);
		
		return new ResponseEntity<>(studentResponse, HttpStatus.OK);
	}
	
	// add mapping for POST /students - add new student or update an existing student
	
	@PostMapping("")
	public ResponseEntity<StudentResponse> addStudent(@RequestBody StudentRequest studentRequest) {
		
		Student student = new ObjectMapper().convertValue(studentRequest, Student.class);

		studentService.save(student);
		
		StudentResponse studentResponse = new ObjectMapper().convertValue(student, StudentResponse.class);

		return new ResponseEntity<>(studentResponse, HttpStatus.CREATED);
	}
		
	// add mapping for DELETE /students/{studentId} - delete student
	
	@DeleteMapping("/{studentId}")
	public String deleteStudent(@PathVariable Integer studentId){
		studentService.deleteById(studentId);
		return "Deleted student id - " + studentId;
	}
}
