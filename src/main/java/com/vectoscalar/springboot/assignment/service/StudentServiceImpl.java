package com.vectoscalar.springboot.assignment.service;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vectoscalar.springboot.assignment.dao.StudentRepository;
import com.vectoscalar.springboot.assignment.entity.Address;
import com.vectoscalar.springboot.assignment.entity.Student;

@Service
public class StudentServiceImpl implements StudentService {
	
	@Autowired
	private StudentRepository studentRepository;
	
	public StudentServiceImpl() {
		
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
	
	public Student findByEmail(String email) {
		Student student = studentRepository.findByEmail(email);
		return student;
	}

	public void save(Student student) {
		Student theStudent = findByEmail(student.getEmail());
		
		if(theStudent != null) {
			student.setId(theStudent.getId());			
		}
		List<Address> addresses = student.getAddresses();
		
		student.setAddresses(null);
		
		for(Address address : addresses)
		{
			student.addAddress(address);
		}
		
		studentRepository.save(student);
	}

	public void deleteById(Integer studentId) {	
		Student student = findById(studentId);
		
		// throw exception if null
		
		if (student == null) {
			throw new EntityNotFoundException("Student id not found - " + studentId);
		}

		for(Address address : student.getAddresses()) {
			address.setDeletedAt(LocalDateTime.now());
		}
		
		student.setDeletedAt(LocalDateTime.now());
		save(student);
	}

}
