package com.services.direct.bean;


import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Invoice {

	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="inv_id")
    private Integer id;
	
	@Column(name="inv_number")
    private String number;
	
	@Column(name="inv_amount")
    private Double amount;
	
	@Column(name="inv_created_date")
	private Date createdDate;
	
	@JsonFormat(pattern = "YYYY-MM-dd")
	@Column(name="inv_Issue_date")
	private Date IssueDate;
	
	@JsonFormat(pattern = "YYYY-MM-dd")
	@Column(name="inv_Return_date")
	private Date ReturnDate;
	
	@Column(name="inv_created_author")
	private String createdAuthor;
	
	@ManyToOne
	@JoinColumn(name = "inv_cmp_id",  nullable = true)
	private Company company;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "invoice", cascade = CascadeType.ALL)
	private List<Payment> payments;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "invoice", cascade = CascadeType.ALL)
	private List<Bordereau> bordereaux;
	
	@Column(name = "inv_pay_down")
	private Boolean payDown;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

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

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getIssueDate() {
		return IssueDate;
	}

	public void setIssueDate(Date issueDate) {
		IssueDate = issueDate;
	}

	public Date getReturnDate() {
		return ReturnDate;
	}

	public void setReturnDate(Date returnDate) {
		ReturnDate = returnDate;
	}

	public String getCreatedAuthor() {
		return createdAuthor;
	}

	public void setCreatedAuthor(String createdAuthor) {
		this.createdAuthor = createdAuthor;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public List<Payment> getPayments() {
		return payments;
	}

	public void setPayments(List<Payment> payments) {
		this.payments = payments;
	}

	public List<Bordereau> getBordereaux() {
		return bordereaux;
	}

	public void setBordereaux(List<Bordereau> bordereaux) {
		this.bordereaux = bordereaux;
	}

	public Boolean getPayDown() {
		return payDown;
	}

	public void setPayDown(Boolean payDown) {
		this.payDown = payDown;
	}
	
}
