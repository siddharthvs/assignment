package com.vectoscalar.springboot.assignment.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vectoscalar.springboot.assignment.dao.AddressRepository;
import com.vectoscalar.springboot.assignment.entity.Address;

@Service
public class AddressService {

	@Autowired
	private AddressRepository addressRepository;
	
	public AddressService() {
		
	}
	
	public List<Address> findAll() {
		return addressRepository.findAll();
	}
	
	public List<Address> findByStudentId(Integer studentId) {
		return addressRepository.findByStudentId(studentId);
	}

	public Address findById(Integer addressId) {
		Address address = addressRepository.findById(addressId).orElse(null);
		
		if (address == null) {
			throw new EntityNotFoundException("Did not find address id - " + addressId);
		}
		
		return address;
	}

	public void save(Address address) {
		addressRepository.save(address);
	}

	public void deleteById(Integer addressId) {
		addressRepository.deleteById(addressId);
	}
}
