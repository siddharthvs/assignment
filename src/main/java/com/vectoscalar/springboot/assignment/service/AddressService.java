package com.vectoscalar.springboot.assignment.service;

import java.util.List;

import com.vectoscalar.springboot.assignment.entity.Address;

public interface AddressService {

	List<Address> findAll();

	List<Address> findByStudentId(Integer studentId);

	Address findById(Integer addressId);

	void save(Address address);

	void deleteById(Integer addressId);

}