package com.services.direct.data;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

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
	
	@JsonFormat(
		      shape = JsonFormat.Shape.STRING,
		      pattern = "YYYY-MM-dd")
		@DateTimeFormat(pattern = "YYYY-MM-dd")
	private String issueDate;
	
	@Nullable
	private String createdAuthor;
	
	@NotNull(message = "customer ID is required.")
	private Integer customer;
	
	@NotNull(message = "bordereauList is required.")
	private List<Integer> bordereaux;
	
	private Boolean payDown;

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

	public Integer getCustomer() {
		return customer;
	}

	public void setCustomer(Integer customer) {
		this.customer = customer;
	}

	public List<Integer> getBordereaux() {
		return bordereaux;
	}

	public void setBordereaux(List<Integer> bordereaux) {
		this.bordereaux = bordereaux;
	}

	public Boolean getPayDown() {
		return payDown;
	}

	public void setPayDown(Boolean payDown) {
		this.payDown = payDown;
	}
	
}
