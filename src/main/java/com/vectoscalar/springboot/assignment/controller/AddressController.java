package com.vectoscalar.springboot.assignment.controller;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vectoscalar.springboot.assignment.request.AddressRequest;
import com.vectoscalar.springboot.assignment.response.AddressResponse;
import com.vectoscalar.springboot.assignment.service.AddressService;
import com.vectoscalar.springboot.assignment.util.CommonUtilities;

@RestController
@RequestMapping("/addresses")
public class AddressController {

	@Autowired
	private AddressService addressService;
	
	private static Logger logger = LoggerFactory.getLogger(AddressController.class);

	public AddressController() {
		
	}
	
	// expose "/addresses" and return list of addresses
	
	@GetMapping("")
	public ResponseEntity<List<AddressResponse>> findAllAddressesByStudentId(@RequestParam Integer studentId) {
		logger.info("Request Recieved to fetch addresses of studentId: "+studentId);
		List<AddressResponse> addresses = addressService.findByStudentId(studentId);
		return new ResponseEntity<>(addresses, HttpStatus.OK);
	}

	// add mapping for GET /addresses/{addressId}
	
	@GetMapping("/{addressId}")
	public ResponseEntity<AddressResponse> getAddress(@PathVariable Integer addressId) {
		logger.info("Request Recieved to fetch addressesId: "+addressId);
		AddressResponse address = addressService.findById(addressId);
		
		if (address == null) {
			logger.info("Did not find AddressId: "+ addressId);
			throw new EntityNotFoundException("Address id not found - " + addressId);
		}
				
		return new ResponseEntity<>(address, HttpStatus.OK);
	}
	
	// add mapping for POST /addresses - add new address or update an existing address
	
	@PostMapping("")
	public ResponseEntity<AddressResponse> addAddress(@RequestBody AddressRequest addressRequest) throws Exception {
		
		logger.info("Add Address Request Recieved -- " + CommonUtilities.convertObjectToJsonString(addressRequest));
		
		addressService.save(addressRequest);
	
		AddressResponse addressResponse = new ObjectMapper().convertValue(addressRequest, AddressResponse.class);

		return new ResponseEntity<>(addressResponse, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{addressId}")
	public String deleteAddress(@PathVariable Integer addressId) {
		logger.info("Request Recieved to delete Address with addressId: " + addressId);

		addressService.deleteById(addressId);
		
		return "Deleted address id - " + addressId;
	}
}
