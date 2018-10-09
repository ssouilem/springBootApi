package com.services.direct.service.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.services.direct.bean.Invoice;
import com.services.direct.bean.Payment;
import com.services.direct.data.PaymentInputDto;
import com.services.direct.repo.InvoiceRepository;
import com.services.direct.repo.PaymentRepository;
import com.services.direct.service.PaymentService;


@Service("paymentService")
@Transactional
// @JsonIgnoreProperties({"JavassistLazyInitializer", "handler"})
public class PaymentServiceImpl implements PaymentService {

	//private ContactMapper contactMapper;
	
	@Autowired
	private ModelMapper modelMapper;
	
	private PaymentRepository paymentRepository;
	
	private InvoiceRepository invoiceRepository;
	
	@Autowired
	public PaymentServiceImpl(final PaymentRepository paymentRepository, final InvoiceRepository invoiceRepository) { //, ContactMapper contactMapper) {
		this.paymentRepository = paymentRepository;
		this.invoiceRepository = invoiceRepository;
		// this.contactMapper = contactMapper;
	}
	
	@Override
	public Payment getPaymentById(Integer id) {
		// return contactRepository.getOne(contactId);
		return paymentRepository.getOne(id);
	}

	@Override
	public Payment addPayment(PaymentInputDto paymentDto) {
		
		// converture dto to entity
		// Contact contact = contactMapper.contactDtoToContact(contactDto);
		Payment payment = modelMapper.map(paymentDto, Payment.class);
		
		Integer invoiceId = paymentDto.getInvoice();
		Invoice invoice = invoiceRepository.getOne(invoiceId); 
		
		 if (invoice != null) {
	         System.out.println("Company already exist in DB : {}" + invoice.getNumber());
			 payment.setInvoice(invoice);
        } else {
        	// throw exception
        }
		return this.paymentRepository.save(payment);

	}

	@Override
	public List<Payment> getAllPayments() {
		List<Payment> contacts = (List<Payment>) paymentRepository.findAll();
		return contacts;
	}

	@Override
	public void deletePayment(Integer id) {
		this.paymentRepository.deleteById(id);
	}
	
	

}
