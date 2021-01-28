package com.vectoscalar.springboot.assignment.service;

import java.util.List;

import com.vectoscalar.springboot.assignment.entity.Student;

public interface StudentService {
	
	List<Student> findAll();
	 
	Student findById(Integer studentId);
	
	Student findByEmail(String email);
	
	void save(Student student);
	
	void deleteById(Integer studentId);
}
