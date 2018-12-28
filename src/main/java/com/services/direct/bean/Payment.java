package com.services.direct.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotEmpty;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.services.direct.utility.BankListEnum;

@Entity
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@JsonIdentityInfo(
		  generator = ObjectIdGenerators.PropertyGenerator.class, 
		  property = "id")
public class Payment {

	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="pay_id")
	private Integer id;
	
    @Column(name = "pay_uid", unique = true, length = 64)
    private String uid;
    
    @JsonFormat(
      shape = JsonFormat.Shape.STRING,
      pattern = "YYYY-MM-dd")
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	@Column(name="pay_created_date")
	private Date createdDate = new Date();
	
	@Column(name="pay_amount")
	private Double amount;
	
	@Column(name="pay_amount_pending")
	private Double amountPending;
	
	@Enumerated(EnumType.STRING)
	@Column(name="pay_bank")
	private BankListEnum bank;
	
	@NotEmpty
	@Column(name="pay_name")
	private String holder;
	
	@JsonInclude(value=Include.NON_EMPTY, content=Include.NON_NULL)
	@OneToMany(orphanRemoval=true, fetch=FetchType.EAGER,
			mappedBy="payment")
	private List<PaymentDetail> paymentDetails = new ArrayList<PaymentDetail>();
	
	@OneToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "pay_inv_id", nullable = false)
	private Invoice invoice;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getAmountPending() {
		return amountPending;
	}

	public void setAmountPending(Double amountPending) {
		this.amountPending = amountPending;
	}

	public BankListEnum getBank() {
		return bank;
	}

	public void setBank(BankListEnum bank) {
		this.bank = bank;
	}

	public String getHolder() {
		return holder;
	}

	public void setHolder(String holder) {
		this.holder = holder;
	}

	public List<PaymentDetail> getPaymentDetails() {
		return paymentDetails;
	}

	public void setPaymentDetails(List<PaymentDetail> paymentDetails) {
		this.paymentDetails = paymentDetails;
	}

	public Invoice getInvoice() {
		return invoice;
	}

	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}

}
