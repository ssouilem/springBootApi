package com.services.direct.service;

import java.util.List;

import com.services.direct.bean.Payment;
import com.services.direct.data.PaymentInputDto;

public interface PaymentService {

	Payment getPaymentById(Integer id);
	
	Payment addPayment(PaymentInputDto invoiceDto);
	
	List<Payment> getAllPayments();
	
	void deletePayment(Integer id);
	
}
