package com.services.direct.service.impl;

import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.services.direct.bean.BordereauDetail;
import com.services.direct.bean.Invoice;
import com.services.direct.bean.Payment;
import com.services.direct.bean.PaymentDetail;
import com.services.direct.data.PaymentInputDto;
import com.services.direct.data.output.PaymentOutputDto;
import com.services.direct.exception.BusinessException;
import com.services.direct.exception.FileNotFoundException;
import com.services.direct.mapping.EntityDTOMapper;
import com.services.direct.repo.InvoiceRepository;
import com.services.direct.repo.PaymentDetailRepository;
import com.services.direct.repo.PaymentRepository;
import com.services.direct.service.PaymentService;
import com.services.direct.utility.Util;


@Service("paymentService")
@Transactional
// @JsonIgnoreProperties({"JavassistLazyInitializer", "handler"})
public class PaymentServiceImpl implements PaymentService {
	
	private static Logger log = LoggerFactory.getLogger(PaymentServiceImpl.class);

	@Autowired
	private EntityDTOMapper entityDTOMapper;
	
	private PaymentRepository paymentRepository;
	
	private PaymentDetailRepository paymentDetailRepository;
	
	private InvoiceRepository invoiceRepository;
	
	@Autowired
	public PaymentServiceImpl(final PaymentRepository paymentRepository, 
			final InvoiceRepository invoiceRepository,
			final PaymentDetailRepository paymentDetailRepository,
			EntityDTOMapper entityDTOMapper) { //, ContactMapper contactMapper) {
		this.paymentRepository = paymentRepository;
		this.invoiceRepository = invoiceRepository;
		this.entityDTOMapper     = entityDTOMapper;
		this.paymentDetailRepository = paymentDetailRepository;
	}
	
	@Override
	public Payment getPaymentByUID(String paymentUid) {
		// return contactRepository.getOne(contactId);
		return paymentRepository.getPaymentByUID(paymentUid);
	}

	@Override
	@Transactional
	public PaymentOutputDto addPayment(PaymentInputDto paymentDto) throws BusinessException {
		
		// converture dto to entity
		// Contact contact = contactMapper.contactDtoToContact(contactDto);
		Payment payment = entityDTOMapper.paymentDtotoPayment(paymentDto);
		
		Invoice invoice = invoiceRepository.getInvoiceByUID(paymentDto.getInvoice());
		// controler et lister les payments
		if (paymentDto.getPaymentDetails() == null || paymentDto.getPaymentDetails().isEmpty()) {
			throw new FileNotFoundException("PAIEMENT_NOT_FOUND");
		} else {
			
			payment.setInvoice(invoice);
			// add UID
			UUID uuid = UUID.randomUUID();
			payment.setUid(uuid.toString());
			Payment paymentEntity = paymentRepository.save(payment);
			// save detail
			List<PaymentDetail> paymentDetails = entityDTOMapper
					.paymentDetailsDtotoPaymentDetails(paymentDto.getPaymentDetails());
			if (paymentDetails != null && paymentDetails.size() != 0) {
				
				paymentDetails.forEach(paymentDetail -> {
					log.info("== paymentDetail detail information : {} ", paymentDetail.getAmount());
				
					paymentDetail.setPayment(paymentEntity);

					// calcule de reste à payer
			
					// save
					paymentDetailRepository.save(paymentDetail);
				});

			} else {
				throw new FileNotFoundException("Error persist resource paymentDetail", "PAIEMENT_NOT_FOUND");
			}
			
			 // payment.getPaymentDetails().stream().map(entity -> paymentDetailRepository.save(entity));
			paymentEntity.setPaymentDetails(paymentDetails);
			return entityDTOMapper.paymentToPaymentOutputDto(paymentEntity);
		}

	}

	@Override
	public List<Payment> getAllPayments() {
		List<Payment> contacts = (List<Payment>) paymentRepository.findAll();
		return contacts;
	}

	@Override
	@Transactional
	public void deletePaymentByUID(String paymentUid) {
		this.paymentRepository.deletePaymentByUID(paymentUid);
	}
	
	

}
