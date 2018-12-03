package com.services.direct.data.output;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.services.direct.utility.PaymentType;

import lombok.Data;

@Data
public class PaymentOutputDetailDto {
	
	@NotNull(message = "type is required.")
	private PaymentType type;

	private Double amount;
	
	private String transactionNumber;
	
	@JsonFormat(
	      shape = JsonFormat.Shape.STRING,
	      pattern = "YYYY-MM-dd")
	@DateTimeFormat(pattern = "YYYY-MM-dd")
	private String issueDate;

	public PaymentType getType() {
		return type;
	}

	public void setType(PaymentType type) {
		this.type = type;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getTransactionNumber() {
		return transactionNumber;
	}

	public void setTransactionNumber(String transactionId) {
		this.transactionNumber = transactionId;
	}

	public String getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(String issueDate) {
		this.issueDate = issueDate;
	}

}
