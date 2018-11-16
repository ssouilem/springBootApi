package com.services.direct.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@JsonIdentityInfo(
		  generator = ObjectIdGenerators.PropertyGenerator.class, 
		  property = "id")
public class Company {

	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="cmp_id")
	private Integer id;
	
    @Column(name = "cmp_uid", unique = true, length = 64)
    private String uid;
	
	@NotNull
	@Column(name="cmp_name")
	private String name;
	
	@Column(name="cmp_address")
	private String address;
	
	@Column(name="cmp_mail")
	private String mail;
	
	@Column(name="cmp_additionalAddress")
	private String additionalAddress;
	
	@Column(name="cmp_zipe_code")
	private String zideCode;
	
	@Column(name="cmp_city")
	private String city;
	
	@Column(name="cmp_phone_number")
	private String phoneNumber;
	
	@Column(name="cmp_fax_number")
	private String faxNumber;
	
	@NotNull
	@Column(name="cmp_tva_number")
	private String tvaNumber;

	public Integer getId() {
		return id;
	}
	
	public void setUid(String uid) {
		this.uid = uid;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUid() {
		return uid;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getAdditionalAddress() {
		return additionalAddress;
	}

	public void setAdditionalAddress(String additionalAddress) {
		this.additionalAddress = additionalAddress;
	}

	public String getZideCode() {
		return zideCode;
	}

	public void setZideCode(String zideCode) {
		this.zideCode = zideCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getFaxNumber() {
		return faxNumber;
	}

	public void setFaxNumber(String faxNumber) {
		this.faxNumber = faxNumber;
	}

	public String getTvaNumber() {
		return tvaNumber;
	}

	public void setTvaNumber(String tvaNumber) {
		this.tvaNumber = tvaNumber;
	}
}