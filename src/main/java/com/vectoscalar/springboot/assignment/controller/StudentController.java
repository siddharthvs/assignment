package com.vectoscalar.springboot.assignment.controller;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vectoscalar.springboot.assignment.entity.Address;
import com.vectoscalar.springboot.assignment.entity.Student;
import com.vectoscalar.springboot.assignment.service.AddressService;
import com.vectoscalar.springboot.assignment.service.StudentService;

@RestController
@RequestMapping("/students")
public class StudentController {

	@Autowired
	private StudentService studentService;
	
	@Autowired
	private AddressService addressService;
	
	public StudentController() {
		
	}
	
	// expose "/students" and return list of students
	
	@GetMapping("")
	public List<Student> findAll() {
		return studentService.findAll();
	}

	// add mapping for GET /students/{studentId}
	
	@GetMapping("/{studentId}")
	public Student getStudent(@PathVariable Integer studentId){
		Student student = studentService.findById(studentId);
		
		if (student == null) {
			throw new EntityNotFoundException("Student id not found - " + studentId);
		}
		
		return student;
	}
	
	// add mapping for POST /students - add new student
	
	@PostMapping("")
	public Student addStudent(@RequestBody Student student) {
		
		// also if in case they pass an id in JSON ... set id to 0
		// this is to force a save of new item ... instead of update
		
		student.setId(0);
		
		studentService.save(student);
		
		return student;
	}
	
	// add mapping for PUT /students - update existing employee
	
	@PutMapping("")
	public Student updateStudent(@RequestBody Student student) {
		
		studentService.save(student);
		
		return student;
	}
		
	// add mapping for DELETE /students/{studentId} - delete student
	
	@DeleteMapping("/{studentId}")
	public String deleteStudent(@PathVariable Integer studentId){
		
		Student student = studentService.findById(studentId);
		
		// throw exception if null
		
		if (student == null) {
			throw new EntityNotFoundException("Student id not found - " + studentId);
		}

		for(Address address : student.getAddresses()) {
			address.setDeletedAt(LocalDateTime.now());
		}
		
		student.setDeletedAt(LocalDateTime.now());
		studentService.save(student);
		
		return "Deleted student id - " + studentId;
	}
}
