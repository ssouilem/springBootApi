package com.services.direct.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.services.direct.utility.BankListEnum;
import com.services.direct.utility.PaymentType;

@Entity
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Payment {

	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="pay_id")
	private Integer id;
	
	@Enumerated(EnumType.STRING)
	@Column(name="pay_type")
	private PaymentType type;
	
	@Column(name="pay_amount")
	private Double amount;
	
	@Enumerated(EnumType.STRING)
	@Column(name="pay_bank")
	private BankListEnum bank;
	
	@ManyToOne
	@JoinColumn(name = "pay_inv_id", nullable = false)
	private Invoice invoice;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public BankListEnum getBank() {
		return bank;
	}

	public void setBank(BankListEnum bank) {
		this.bank = bank;
	}

	public Invoice getInvoice() {
		return invoice;
	}

	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}
	
	
}
