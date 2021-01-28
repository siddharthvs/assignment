package com.vectoscalar.springboot.assignment.controller;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vectoscalar.springboot.assignment.entity.Address;
import com.vectoscalar.springboot.assignment.entity.Student;
import com.vectoscalar.springboot.assignment.request.AddressRequest;
import com.vectoscalar.springboot.assignment.response.AddressResponse;
import com.vectoscalar.springboot.assignment.response.StudentResponse;
import com.vectoscalar.springboot.assignment.service.AddressService;
import com.vectoscalar.springboot.assignment.service.StudentService;

@RestController
@RequestMapping("/addresses")
public class AddressController {

	@Autowired
	private AddressService addressService;
	
	public AddressController() {
		
	}
	
	// expose "/addresses" and return list of addresses
	
	@GetMapping("")
	public ResponseEntity<List<AddressResponse>> findAllByStudentId(@RequestParam Integer studentId) {
		List<Address> addresses = addressService.findByStudentId(studentId);
		List<AddressResponse> addressResponse = new ObjectMapper().convertValue(addresses,  new TypeReference<List<AddressResponse>>() {});
		return new ResponseEntity<>(addressResponse, HttpStatus.OK);
	}

	// add mapping for GET /addresses/{addressId}
	
	@GetMapping("/{addressId}")
	public ResponseEntity<AddressResponse> getAddress(@PathVariable Integer addressId) {
		
		Address address = addressService.findById(addressId);
		
		if (address == null) {
			throw new EntityNotFoundException("Address id not found - " + addressId);
		}
		
		AddressResponse addressResponse = new ObjectMapper().convertValue(address, AddressResponse.class);
		
		return new ResponseEntity<>(addressResponse, HttpStatus.OK);
	}
	
	// add mapping for POST /addresses - add new address or update an existing address
	
	@PostMapping("")
	public ResponseEntity<AddressResponse> addAddress(@RequestBody AddressRequest addressRequest) {
		
		Address address = new ObjectMapper().convertValue(addressRequest, Address.class);
		
		addressService.save(address);
	
		AddressResponse addressResponse = new ObjectMapper().convertValue(address, AddressResponse.class);

		return new ResponseEntity<>(addressResponse, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{addressId}")
	public String deleteAddress(@PathVariable Integer addressId) {
		
		addressService.deleteById(addressId);
		
		return "Deleted address id - " + addressId;
	}
}
