package com.services.direct.data;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class InvoiceInputDto {
	
	@NotNull(message = "Invoice number is required.")
    private String number;
	
	@NotEmpty(message = "amount not empty")
    private Double amount;
	
	private Double otherExpenses;
	
	@JsonFormat(
		      shape = JsonFormat.Shape.STRING,
		      pattern = "YYYY-MM-dd")
		@DateTimeFormat(pattern = "YYYY-MM-dd")
	private String issueDate;
	
	@Nullable
	private String createdAuthor;
	
	@NotNull(message = "customer ID is required.")
	private String customer;
	
	@NotNull(message = "bordereauList is required.")
	private List<BordereauUidDto> bordereaux;
	
	private Boolean payDown;

	private String remarks;
	
	private Boolean sumInLetter;
	
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getOtherExpenses() {
		return otherExpenses;
	}

	public void setOtherExpenses(Double otherExpenses) {
		this.otherExpenses = otherExpenses;
	}

	public String getIssueDate() {
		return this.issueDate;
	}
	
	public String getIssueDateStr() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.format(issueDate);
	}

	public void setIssueDate(String issueDate) {
		this.issueDate = issueDate;
	}

	public String getCreatedAuthor() {
		return createdAuthor;
	}

	public void setCreatedAuthor(String createdAuthor) {
		this.createdAuthor = createdAuthor;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public List<BordereauUidDto> getBordereaux() {
		return bordereaux;
	}

	public void setBordereaux(List<BordereauUidDto> bordereaux) {
		this.bordereaux = bordereaux;
	}

	public Boolean getPayDown() {
		return payDown;
	}

	public void setPayDown(Boolean payDown) {
		this.payDown = payDown;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Boolean getSumInLetter() {
		return sumInLetter;
	}

	public void setSumInLetter(Boolean sumInLetter) {
		this.sumInLetter = sumInLetter;
	}
	
	
	
}
