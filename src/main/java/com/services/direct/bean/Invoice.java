package com.services.direct.bean;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@JsonIdentityInfo(
		  generator = ObjectIdGenerators.PropertyGenerator.class, 
		  property = "id")
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
	
	@JsonFormat(
		      shape = JsonFormat.Shape.STRING,
		      pattern = "YYYY-MM-dd")
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	@Column(name="inv_issue_date")
	private Date issueDate;
	
	@Column(name="inv_created_author")
	private String createdAuthor;
	
	@ManyToOne
	@JoinColumn(name = "inv_cmp_id",  nullable = true)
	private Company company;
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@JsonInclude(value=Include.NON_EMPTY, content=Include.NON_NULL)
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "invoice")
	private List<Payment> payments;
	
	@JsonInclude(value=Include.NON_EMPTY, content=Include.NON_NULL)
	@OneToMany(fetch=FetchType.EAGER,
			//cascade=CascadeType.ALL,
			mappedBy="invoice")
	private List<Bordereau> bordereaux = new ArrayList<Bordereau>();
	
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
		return this.issueDate;
	}

	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
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

	public void addBordereau(Bordereau bordereau) {
		this.bordereaux.add(bordereau);
	}
	
	public Boolean getPayDown() {
		return payDown;
	}

	public void setPayDown(Boolean payDown) {
		this.payDown = payDown;
	}
	
}
