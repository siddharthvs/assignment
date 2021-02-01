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
import com.vectoscalar.springboot.assignment.dao.AddressRepository;
import com.vectoscalar.springboot.assignment.dao.StudentRepository;
import com.vectoscalar.springboot.assignment.entity.Address;
import com.vectoscalar.springboot.assignment.entity.Student;
import com.vectoscalar.springboot.assignment.request.AddressRequest;
import com.vectoscalar.springboot.assignment.response.AddressResponse;

@Service
public class AddressServiceImpl implements AddressService {

	@Autowired
	private AddressRepository addressRepository;
	
	@Autowired
	private StudentRepository studentRepository;
	
	@Autowired
	private StudentService studentService;

	private static Logger logger = LoggerFactory.getLogger(AddressServiceImpl.class);

	
	public AddressServiceImpl() {
		
	}
	
	@Override
	public List<AddressResponse> findByStudentId(Integer studentId) {
		logger.info("Fetching Addresses for studentId: "+ studentId);
		List<Address> addresses = addressRepository.findByStudentId(studentId);
		logger.info("Addresses Fetched");
		return new ObjectMapper().convertValue(addresses,  new TypeReference<List<AddressResponse>>() {});
	}

	@Override
	public AddressResponse findById(Integer addressId) {
		logger.info("Fetching AddressId: "+ addressId);
		Address address = addressRepository.findById(addressId).orElse(null);
		
		if (address == null) {
			logger.info("Did not find AddressId: "+ addressId);
			throw new EntityNotFoundException("Did not find address id - " + addressId);
		}
		
		return new ObjectMapper().convertValue(address, AddressResponse.class);		
	}

	@Override
	public void save(AddressRequest addressRequest) {
		if(addressRequest.getStudentId() == 0 || addressRequest.getStudentId() == null) {
			logger.info("Student Id not present in Request");
			throw new RuntimeException("Student Id not present in Request");
		}
		logger.info("Adding Address for studentId: "+ addressRequest.getStudentId());
		Address address = new ObjectMapper().convertValue(addressRequest, Address.class);
		Student student = studentRepository.findById(addressRequest.getStudentId()).orElse(null);
		student.addAddress(address);
		addressRepository.save(address);
		logger.info("Address saved for studentId: "+ addressRequest.getStudentId());
	}

	@Override
	public void deleteById(Integer addressId) {
		Address address = addressRepository.findById(addressId).orElse(null);
		
		// throw exception if null
		
		if (address == null) {
			logger.info("Did not find AddressId: "+ addressId);
			throw new EntityNotFoundException("Address id not found - " + addressId);
		}

		logger.info("Deleting AddressId: "+ addressId);
		address.setDeletedAt(LocalDateTime.now());
		addressRepository.save(address);
	}
}
