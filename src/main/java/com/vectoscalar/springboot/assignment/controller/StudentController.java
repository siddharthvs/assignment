package com.vectoscalar.springboot.assignment.controller;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.vectoscalar.springboot.assignment.request.StudentRequest;
import com.vectoscalar.springboot.assignment.response.StudentResponse;
import com.vectoscalar.springboot.assignment.service.StudentService;
import com.vectoscalar.springboot.assignment.util.CommonUtilities;

@RestController
@RequestMapping("/students")
public class StudentController {

	@Autowired
	private StudentService studentService;

	private static Logger logger = LoggerFactory.getLogger(StudentController.class);

	public StudentController() {
		
	}
	
	// expose "/students" and return list of students
	
	@GetMapping("")
	public ResponseEntity<List<StudentResponse>> findAllStudents() {
		logger.info("Request Recieved to fetch all the Students...");
		List<StudentResponse> students = studentService.findAll();
		return new ResponseEntity<>(students, HttpStatus.OK);
	}

	// add mapping for GET /students/{studentId}
	
	@GetMapping("/{studentId}")
	public ResponseEntity<StudentResponse> getStudent(@PathVariable Integer studentId){
		logger.info("Request Recieved to fetch Student with studentId: " + studentId);

		StudentResponse student = studentService.findById(studentId);
		
		if (student == null) {
			throw new EntityNotFoundException("Student id not found - " + studentId);
		}
		
		return new ResponseEntity<>(student, HttpStatus.OK);
	}
	
	// add mapping for POST /students - add new student or update an existing student
	
	@PostMapping("")
	public ResponseEntity<StudentResponse> addStudent(@RequestBody StudentRequest studentRequest) throws Exception {
		logger.info("Add Student Request Recieved -- " + CommonUtilities.convertObjectToJsonString(studentRequest));
		
		if(studentRequest.getEmail() == null) {
			logger.info("Student Request does not contain email...");
			throw new RuntimeException("Email not provided in the Request.");
		}
		
		StudentResponse savedStudent = studentService.createOrUpdate(studentRequest);
		return new ResponseEntity<>(savedStudent, HttpStatus.CREATED);
	}
		
	// add mapping for DELETE /students/{studentId} - delete student
	// Performing Soft Delete for Student and associated Addresses
	@DeleteMapping("/{studentId}")
	public String deleteStudent(@PathVariable Integer studentId){
		logger.info("Request Recieved to delete Student with studentId: " + studentId);
		studentService.deleteById(studentId);
		return "Deleted student id - " + studentId;
	}
}
