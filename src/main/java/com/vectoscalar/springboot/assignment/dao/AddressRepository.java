package com.vectoscalar.springboot.assignment.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vectoscalar.springboot.assignment.entity.Address;

public interface AddressRepository extends JpaRepository<Address, Integer> {

	// code...

	List<Address> findByStudentId(Integer studentId);
}
