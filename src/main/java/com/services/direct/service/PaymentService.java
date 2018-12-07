package com.services.direct.service;

import java.util.List;

import com.services.direct.bean.Payment;
import com.services.direct.bean.security.User;
import com.services.direct.data.PaymentInputDto;
import com.services.direct.data.output.PaymentOutputDto;
import com.services.direct.exception.BusinessException;

public interface PaymentService {

	Payment getPaymentByUID(String paymentUid);
	
	PaymentOutputDto addPayment(PaymentInputDto paymentDto, User user) throws BusinessException;
	
	List<Payment> getAllPayments(Integer companyId);
	
	void deletePaymentByUID(String paymentUid);
	
}
