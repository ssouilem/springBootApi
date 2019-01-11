package com.services.direct.data.output;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class CustomerDto {

	@NotNull
	private String uid;
	
	@NotNull
	private String name;
	
	private String address;
	
	private String additionalAddress;
	
	private String postalCode;
	
	private String city;
    @Email
    @Size(max = 100)
	private String mail;
    
    private String siret;

    
	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
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

	public String getAdditionalAddress() {
		return additionalAddress;
	}

	public void setAdditionalAddress(String additionalAddress) {
		this.additionalAddress = additionalAddress;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getSiret() {
		return siret;
	}

	public void setSiret(String siret) {
		this.siret = siret;
	}
	
}
