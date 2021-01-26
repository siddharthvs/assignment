package com.vectoscalar.springboot.assignment.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vectoscalar.springboot.assignment.dao.StudentRepository;
import com.vectoscalar.springboot.assignment.entity.Student;

@Service
public class StudentService {
	
	@Autowired
	private StudentRepository studentRepository;
	
	public StudentService() {
		
	}
	
	public List<Student> findAll() {
		return studentRepository.findAll();
	}

	public Student findById(Integer studentId) {
		Student student = studentRepository.findById(studentId).orElse(null);
		
		if (student == null) {
			throw new EntityNotFoundException("Did not find student id - " + studentId);
		}
		
		return student;
	}

	public void save(Student theStudent) {
		studentRepository.save(theStudent);
	}

	public void deleteById(Integer studentId) {
		studentRepository.deleteById(studentId);
	}

}
