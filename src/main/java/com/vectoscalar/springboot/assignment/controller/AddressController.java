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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vectoscalar.springboot.assignment.entity.Address;
import com.vectoscalar.springboot.assignment.entity.Student;
import com.vectoscalar.springboot.assignment.service.AddressService;
import com.vectoscalar.springboot.assignment.service.StudentService;

@RestController
@RequestMapping("/addresses")
public class AddressController {

	@Autowired
	private AddressService addressService;
	
	@Autowired
	private StudentService studentService;
	
	public AddressController() {
		
	}
	
	// expose "/addresses" and return list of addresses
	
	@GetMapping("")
	public List<Address> findAllByStudentId(@RequestParam Integer studentId) {
		return addressService.findByStudentId(studentId);
	}

	// add mapping for GET /addresses/{addressId}
	
	@GetMapping("/{addressId}")
	public Address getAddress(@PathVariable Integer addressId) {
		
		Address address = addressService.findById(addressId);
		
		if (address == null) {
			throw new EntityNotFoundException("Address id not found - " + addressId);
		}
		
		return address;
	}
	
	// add mapping for POST /addresses - add new address
	
	@PostMapping("")
	public Address addAddress(@RequestBody Address address) {
		
		// also if in case they pass an id in JSON ... set id to 0
		// this is to force a save of new item ... instead of update
		
		address.setId(0);
		
		addressService.save(address);
		
		return address;
	}
	
	// add mapping for PUT /addresses - update existing address
	
	@PutMapping("")
	public Address updateAddress(@RequestBody Address address) {
		
		addressService.save(address);
		
		return address;
	}
		
	// add mapping for DELETE /addresses/{addressId} - delete address
	
	@DeleteMapping("/{addressId}")
	public String deleteAddress(@PathVariable Integer addressId) {
		
		Address address = addressService.findById(addressId);
		
		// throw exception if null
		
		if (address == null) {
			throw new EntityNotFoundException("Address id not found - " + addressId);
		}

		address.setDeletedAt(LocalDateTime.now());
		addressService.save(address);
		
		return "Deleted address id - " + addressId;
	}
}
