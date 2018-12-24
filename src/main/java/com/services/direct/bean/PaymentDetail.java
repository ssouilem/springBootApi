package com.services.direct.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.services.direct.utility.PaymentType;

@Entity
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@JsonIdentityInfo(
		  generator = ObjectIdGenerators.PropertyGenerator.class, 
		  property = "id")
public class PaymentDetail {

	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="payd_id")
	private Integer id;
	
	@JsonFormat(
      shape = JsonFormat.Shape.STRING,
      pattern = "YYYY-MM-dd")
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	@Column(name="payd_created_date")
	private Date createdDate = new Date();

	@Enumerated(EnumType.STRING)
	@Column(name="payd_type")
	private PaymentType type;
	
	@Column(name="payd_amount")
	private Double amount;
	
	@Column(name="payd_transactionId")
	private String transactionNumber;
	
	@JsonFormat(
		      shape = JsonFormat.Shape.STRING,
		      pattern = "YYYY-MM-dd")
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	@Column(name="payd_issue_date")
	private Date issueDate;
	
	@ManyToOne
	@JoinColumn(name = "payd_pay_id", nullable = false)
	private Payment payment;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

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

	public Date getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

		
	
}
