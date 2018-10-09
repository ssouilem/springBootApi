package com.services.direct.data;


import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.lang.Nullable;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class InvoiceInputDto {
	
	@NotEmpty
    private String number;
	
	@NotEmpty
    private Double amount;
	
	@JsonFormat(pattern = "YYYY-MM-dd")
	private Date issueDate;
	
	@Nullable
	@JsonFormat(pattern = "YYYY-MM-dd")
	private Date returnDate;
	
	@Nullable
	private String createdAuthor;
	
	@NotNull
	private Integer company;
	
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

	public Date getIssueDate() {
		return this.issueDate;
	}

	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}

	public Date getReturnDate() {
		return this.returnDate;
	}

	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}

	public String getCreatedAuthor() {
		return createdAuthor;
	}

	public void setCreatedAuthor(String createdAuthor) {
		this.createdAuthor = createdAuthor;
	}

	public Integer getCompany() {
		return company;
	}

	public void setCompany(Integer company) {
		this.company = company;
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
