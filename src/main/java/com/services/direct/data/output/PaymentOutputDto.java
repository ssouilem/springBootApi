package com.services.direct.data.output;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.services.direct.bean.BordereauDetail;
import com.services.direct.utility.BankListEnum;

import lombok.Data;

@Data
public class PaymentOutputDto {

	private String uid;
	
	private BankListEnum bank;
	
	@NotNull(message = "holder is required.")
	private String holder;

	private List<PaymentOutputDetailDto> paymentDetails = new ArrayList<PaymentOutputDetailDto>();
	
	private InvoiceOutputDto invoice;

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

	public List<PaymentOutputDetailDto> getPaymentDetails() {
		return paymentDetails;
	}

	public void setPaymentDetails(List<PaymentOutputDetailDto> paymentDetails) {
		this.paymentDetails = paymentDetails;
	}

	public void addPaymentDetail(PaymentOutputDetailDto paymentDetail) {
		paymentDetails.add(paymentDetail);
	}
	
	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public InvoiceOutputDto getInvoice() {
		return invoice;
	}

	public void setInvoice(InvoiceOutputDto invoice) {
		this.invoice = invoice;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
	
}
