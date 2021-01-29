package com.vectoscalar.springboot.assignment.service;

import java.util.List;

import com.vectoscalar.springboot.assignment.request.StudentRequest;
import com.vectoscalar.springboot.assignment.response.StudentResponse;

public interface StudentService {
	
	List<StudentResponse> findAll();
	 
	StudentResponse findById(Integer studentId);
	
	StudentResponse findByEmail(String email);
	
	StudentResponse createOrUpdate(StudentRequest student);
	
	void deleteById(Integer studentId);
}
