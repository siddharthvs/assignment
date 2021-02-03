package com.vectoscalar.springboot.assignment.service;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vectoscalar.springboot.assignment.dao.StudentRepository;
import com.vectoscalar.springboot.assignment.entity.Address;
import com.vectoscalar.springboot.assignment.entity.Student;
import com.vectoscalar.springboot.assignment.request.StudentRequest;
import com.vectoscalar.springboot.assignment.response.StudentResponse;

@Service
public class StudentServiceImpl implements StudentService {
	
	@Autowired
	private StudentRepository studentRepository;
	
    private static Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

	public StudentServiceImpl() {
		
	}
	
	public List<StudentResponse> findAll() {
		logger.info("Fetching all Students.....");
		List<Student> students = studentRepository.findAll();
		logger.info("Students Fetched.....");
		return new ObjectMapper().convertValue(students,  new TypeReference<List<StudentResponse>>() {});

	}

	public StudentResponse findById(Integer studentId) {
		logger.info("Fetching Student Id : "+ studentId);

		Student student = studentRepository.findById(studentId).orElse(null);
		
		if (student == null) {
			logger.info("Did not find studentId: " + studentId);
			throw new EntityNotFoundException("Did not find student id - " + studentId);
		}
		logger.info("Student Fetched.....");
		return new ObjectMapper().convertValue(student, StudentResponse.class);
	}
	
	public StudentResponse findByEmail(String email) {
		logger.info("Fetching Student with Email : "+ email);
		Student student = studentRepository.findByEmail(email);
		return new ObjectMapper().convertValue(student, StudentResponse.class);
	}

	public StudentResponse createOrUpdate(StudentRequest studentRequest) {
		
			if(studentRequest.getEmail() == null) {
				logger.info("Student Request does not contain email...");
				throw new RuntimeException("Email not provided in the Request.");
			}
		Student studentFromRequest = new ObjectMapper().convertValue(studentRequest, Student.class);
		
		logger.info("Checking if a student already exists with the provided email...");
		Student studentFromDB = studentRepository.findByEmail(studentRequest.getEmail());
		
		if(studentFromDB != null) {
			logger.info("Student already exists with email: "+studentRequest.getEmail());
			studentFromRequest.setId(studentFromDB.getId());	
			logger.info("Updating the existing student");
		}else {
			logger.info("Student doesn't exists with email: "+studentRequest.getEmail());
			logger.info("Creating a new student");
		}
//		if(studentFromRequest.getAddresses() != null) {
//			logger.info("Adding provided Addresses");
//
//			List<Address> addresses = studentFromRequest.getAddresses();
//			
//			studentFromRequest.setAddresses(null);
//			
//			for(Address address : addresses)
//			{
//				studentFromRequest.addAddress(address);
//			}
//		} // TO BE COMMENTED 
//		
		Student savedStudent = studentRepository.save(studentFromRequest);
		return new ObjectMapper().convertValue(savedStudent, StudentResponse.class);
	}

	public void deleteById(Integer studentId) {	
		Student student = studentRepository.findById(studentId).orElse(null);
		
		// throw exception if null
		
		if (student == null) {
			logger.info("Did not find studentId: " + studentId);
			throw new EntityNotFoundException("Student id not found - " + studentId);
		}
		
		logger.info("Deleting Addresses associated with  studentId : "+ studentId);
		for(Address address : student.getAddresses()) {
			logger.info("Deleting addressId : "+ address.getId());
			address.setDeletedAt(LocalDateTime.now());
		}
		
		logger.info("Deleting Student Id : "+ studentId);
		student.setDeletedAt(LocalDateTime.now());
		studentRepository.save(student);
	}

}
