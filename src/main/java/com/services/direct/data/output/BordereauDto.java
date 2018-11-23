package com.services.direct.data.output;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.services.direct.utility.BrType;

import lombok.Data;

@Data
public class BordereauDto {
	

	@NotNull(message = "number is required.")
	private String number;
	
	private String uid;

	@NotNull(message = "type is required.")
	private BrType type;

	private String createdAuthor;

	@NotNull(message = "customer uid is required.")
	private CustomerDto customer;
	
	private String createdDate;

	private List<BordereauDetailDto> bordereauDetailList;

	private String treatmentDate;
	
	private String invoiceNumber;
	
	private Double subTotal = 0.0;

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public BrType getType() {
		return type;
	}

	public void setType(BrType type) {
		this.type = type;
	}

	public String getCreatedAuthor() {
		return createdAuthor;
	}

	public void setCreatedAuthor(String createdAuthor) {
		this.createdAuthor = createdAuthor;
	}

	public CustomerDto getCustomer() {
		return customer;
	}

	public void setCustomer(CustomerDto customer) {
		this.customer = customer;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public List<BordereauDetailDto> getBordereauDetailList() {
		return bordereauDetailList;
	}

	public void setBordereauDetailList(List<BordereauDetailDto> bordereauDetailList) {
		this.bordereauDetailList = bordereauDetailList;
	}

	public String getTreatmentDate() {
		return treatmentDate;
	}

	public void setTreatmentDate(String treatmentDate) {
		this.treatmentDate = treatmentDate;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public Double getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(Double subTotal) {
		this.subTotal = subTotal;
	}

	

}
