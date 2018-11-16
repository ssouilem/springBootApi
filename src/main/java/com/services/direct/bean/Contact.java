package com.services.direct.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.services.direct.utility.Gender;

import io.swagger.annotations.ApiModelProperty;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Contact {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ct_id")
	@ApiModelProperty(notes = "The database generated contact ID")
	private Integer id;
	
    @Column(name = "ct_uid", unique = true, length = 64)
    private String uid;
    
	@Enumerated(EnumType.STRING)
    @Column(length = 10, name="ct_gender")
    private Gender gender;
	
	@NotEmpty
	@Column(name="ct_first_name")
	private String firstName;
	
	@NotEmpty
	@Column(name="ct_last_name")
	private String lastName;
	
	
	@Column(name="ct_phone_number")
	private String phoneNumber;
	
	@NotNull
    @Email
    @Size(max = 100)
	@Column(name="ct_email")
    private String email;
	
	@JsonBackReference
	@OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ct_cus_id", nullable = false)
    private Customer customer;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
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

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
}
