package com.vectoscalar.springboot.assignment.service;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vectoscalar.springboot.assignment.dao.AddressRepository;
import com.vectoscalar.springboot.assignment.entity.Address;

@Service
public class AddressServiceImpl implements AddressService {

	@Autowired
	private AddressRepository addressRepository;
	
	public AddressServiceImpl() {
		
	}
	
	@Override
	public List<Address> findAll() {
		return addressRepository.findAll();
	}
	
	@Override
	public List<Address> findByStudentId(Integer studentId) {
		return addressRepository.findByStudentId(studentId);
	}

	@Override
	public Address findById(Integer addressId) {
		Address address = addressRepository.findById(addressId).orElse(null);
		
		if (address == null) {
			throw new EntityNotFoundException("Did not find address id - " + addressId);
		}
		
		return address;
	}

	@Override
	public void save(Address address) {
		addressRepository.save(address);
	}

	@Override
	public void deleteById(Integer addressId) {
		Address address = findById(addressId);
		
		// throw exception if null
		
		if (address == null) {
			throw new EntityNotFoundException("Address id not found - " + addressId);
		}

		address.setDeletedAt(LocalDateTime.now());
		save(address);
	}
}
