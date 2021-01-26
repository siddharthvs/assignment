package com.vectoscalar.springboot.assignment.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name="student")
@Where(clause = "deleted_at is null")
public class Student{
	
	// define fields
	
		@Id
		@GeneratedValue(strategy=GenerationType.IDENTITY)
		@Column(name="id")
		private int id;
		
		@Column(name="first_name", nullable = false)
		private String firstName;
		
		@Column(name="last_name")
		private String lastName;
		
		@Column(name="email", unique=true)
		private String email;
		
		@OneToMany(mappedBy="student", fetch = FetchType.LAZY)
		private List<Address> addresses;
		
		@CreationTimestamp
		@Column(name="created_at", updatable=false)
		private LocalDateTime createdAt;
	    
		@UpdateTimestamp
	    @Column(name = "updated_at")
		private LocalDateTime updatedAt;
	 
	    @Column(name = "deleted_at")
		private LocalDateTime deletedAt;
			
		// define constructors
		
		public Student() {
			
		}

		public Student(String firstName, String lastName, String email) {
			this.firstName = firstName;
			this.lastName = lastName;
			this.email = email;
		}

		// define getter/setter
		
		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getFirstName() {
			return firstName;
		}

		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}

		public String getLastName() {
			return lastName;
		}

		public void setLastName(String lastName) {
			this.lastName = lastName;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public LocalDateTime getCreatedAt() {
			return createdAt;
		}

		public void setCreatedAt(LocalDateTime createdAt) {
			this.createdAt = createdAt;
		}

		public LocalDateTime getUpdatedAt() {
			return updatedAt;
		}

		public void setUpdatedAt(LocalDateTime updatedAt) {
			this.updatedAt = updatedAt;
		}

		public LocalDateTime getDeletedAt() {
			return deletedAt;
		}

		public void setDeletedAt(LocalDateTime deletedAt) {
			this.deletedAt = deletedAt;
		}
		
		public List<Address> getAddresses() {
			return addresses;
		}

		public void setAddresses(List<Address> addresses) {
			this.addresses = addresses;
		}

		// define tostring

		@Override
		public String toString() {
			return "Student [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + "]";
		}
		
		public void addAddress(Address address) {
			if(addresses == null) {
				addresses = new ArrayList<>();
			}
			
			addresses.add(address);
			address.setStudent(this);
		}
			
}
