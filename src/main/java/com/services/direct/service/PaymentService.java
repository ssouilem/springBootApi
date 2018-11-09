package com.services.direct.service;

import java.util.List;

import com.services.direct.bean.Payment;
import com.services.direct.data.PaymentInputDto;

public interface PaymentService {

	Payment getPaymentByUID(String paymentUid);
	
	Payment addPayment(PaymentInputDto paymentDto);
	
	List<Payment> getAllPayments();
	
	void deletePaymentByUID(String paymentUid);
	
}
