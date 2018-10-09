package com.services.direct.data;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class CompanyInputDto {

	@NotNull
	private String name;
	
	private String address;
	
	private String city;
	
	private String phoneNumber;
	
	private String faxNumber;
	
	@NotNull
	private String tvaNumber;

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
