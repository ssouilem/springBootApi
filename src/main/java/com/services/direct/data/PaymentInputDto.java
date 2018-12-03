package com.services.direct.data;

import java.util.List;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.services.direct.utility.BankListEnum;

import lombok.Data;

@Data
public class PaymentInputDto {

	private BankListEnum bank;
	
	@NotNull(message = "holder is required.")
	private String holder;

	private List<PaymentDetailDto> paymentDetails;
	
	private String invoice;

	@NotEmpty
    private Double amount;
	
	private Double amountPending;

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

	public List<PaymentDetailDto> getPaymentDetails() {
		return paymentDetails;
	}

	public void setPaymentDetails(List<PaymentDetailDto> paymentDetails) {
		this.paymentDetails = paymentDetails;
	}

	public String getInvoice() {
		return invoice;
	}

	public void setInvoice(String invoice) {
		this.invoice = invoice;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
	
}
