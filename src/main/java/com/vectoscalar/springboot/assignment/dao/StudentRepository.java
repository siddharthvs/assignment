package com.vectoscalar.springboot.assignment.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vectoscalar.springboot.assignment.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Integer> {
	
	// code...
	
}
