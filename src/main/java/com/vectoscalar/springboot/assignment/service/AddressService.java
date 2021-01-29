package com.vectoscalar.springboot.assignment.service;

import java.util.List;

import com.vectoscalar.springboot.assignment.request.AddressRequest;
import com.vectoscalar.springboot.assignment.response.AddressResponse;

public interface AddressService {

	List<AddressResponse> findByStudentId(Integer studentId);

	AddressResponse findById(Integer addressId);

	void save(AddressRequest address);

	void deleteById(Integer addressId);

}