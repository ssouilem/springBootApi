package com.services.direct.mapping;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeMap;
import org.modelmapper.TypeToken;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.services.direct.bean.Bordereau;
import com.services.direct.bean.BordereauDetail;
import com.services.direct.bean.Contact;
import com.services.direct.bean.Contract;
import com.services.direct.bean.Customer;
import com.services.direct.bean.Invoice;
import com.services.direct.bean.Payment;
import com.services.direct.bean.PaymentDetail;
import com.services.direct.bean.Product;
import com.services.direct.bean.Reduction;
import com.services.direct.data.BordereauDetailDto;
import com.services.direct.data.BordereauInputDto;
import com.services.direct.data.BordereauUidDto;
import com.services.direct.data.ContactInputDto;
import com.services.direct.data.ContractInputDto;
import com.services.direct.data.CustomerInputDto;
import com.services.direct.data.InvoiceInputDto;
import com.services.direct.data.PaymentDetailDto;
import com.services.direct.data.PaymentInputDto;
import com.services.direct.data.ProductInputDto;
import com.services.direct.data.ReductionDto;
import com.services.direct.data.output.BordereauDto;
import com.services.direct.data.output.InvoiceOutputDto;
import com.services.direct.data.output.PaymentOutputDetailDto;
import com.services.direct.data.output.PaymentOutputDto;
import com.services.direct.repo.BordereauRepository;
import com.services.direct.repo.ProductRepository;

@Component
public class EntityDTOMapper {

	ModelMapper modelMapper = new ModelMapper();

	private ProductRepository productRepository;
	private BordereauRepository bordereauRepository;

	@Autowired
	public EntityDTOMapper(ProductRepository productRepository, BordereauRepository bordereauRepository) {
		this.productRepository = productRepository;
		this.bordereauRepository = bordereauRepository;
	}

	public EntityDTOMapper() {
		System.out.println("#############   EntityDTOMapper ############################ ");
		modelMapper.getConfiguration().setAmbiguityIgnored(true);
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		/* Mappage DTO -> Entity */
		modelMapper.addMappings(new PropertyMap<ContactInputDto, Contact>() {
			protected void configure() {
				map().setFirstName(source.getFirstName());
				skip().setCustomer(null);
			}
		});
		modelMapper.addMappings(new PropertyMap<BordereauDetailDto, BordereauDetail>() {
			protected void configure() {
					map().setPercentage(source.getPercentage());
					map().setQte(source.getQte());
					skip().setId(0);
					skip().setTotalCommandLine(null);
			}
		});
		
//		

		modelMapper.addMappings(new PropertyMap<InvoiceInputDto, Invoice>() {
			protected void configure() {
					skip().setId(0);
					skip().setBordereaux(null);
			}
		});
		
//		modelMapper.addMappings(new PropertyMap<PaymentDetailDto, PaymentDetail>() {
//			protected void configure() {
//				map().setTransactionId(source.getTransaction());
//				skip().setId(0);
//			}
//		});
		
		// modelMapper.addMappings(new contractDTOToContractMapping());
		modelMapper.addMappings(new productDTOToProductMapping());
		modelMapper.addMappings(new customerDTOtoCustomerMapping());
		
		modelMapper.addMappings(new productToProductDTOMapping());
		modelMapper.addMappings(new contractToContractDTOMapping());
		modelMapper.addMappings(new PropertyMap<ReductionDto, Reduction>() {
			protected void configure() {
					map().setProductUid(source.getProductUid());
					skip().setId(0);
					skip().setProduct(null);
			}
		});

		System.out.println("#############   EntityDTOMapper ############################ "
				+ modelMapper.getConfiguration().toString());

		/* Mappage Entity -> DTO*/
		modelMapper.addMappings(new PropertyMap<Invoice, InvoiceOutputDto>() {
			protected void configure() {
					skip().setIssueDate(null);
			}
		});
	}

	Converter<String, Date> toStringDate = new AbstractConverter<String, Date>() {
		@Override
		protected Date convert(String source) {
			String dateFormat = "yyyy-MM-dd";
			try {
				return new SimpleDateFormat(dateFormat).parse(source);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		}
	};
	
	Converter<Date, String> toDateString = new AbstractConverter<Date, String>() {
		
		@Override
		protected String convert(Date source) {
			String dateFormat = "yyyy-MM-dd";
			
			DateFormat df = new SimpleDateFormat(dateFormat);
			return df.format(source);
		}
	};

	public Invoice invoiceDtotoInvoice(InvoiceInputDto invoiceDto) {
		

		TypeMap<InvoiceInputDto, Invoice> invoiceDtotoInvoice = (modelMapper.getTypeMap(InvoiceInputDto.class, Invoice.class) == null) ? 
				modelMapper.createTypeMap(InvoiceInputDto.class, Invoice.class) : 
					modelMapper.getTypeMap(InvoiceInputDto.class, Invoice.class);
		
				invoiceDtotoInvoice.addMappings(mapping -> {
	    	mapping.using(toStringDate).map(InvoiceInputDto::getIssueDate, Invoice::setIssueDate);
	    });
		Invoice invoice = modelMapper.map(invoiceDto, Invoice.class);
		if (!invoice.getBordereaux().isEmpty())	invoice.getBordereaux().clear();
		return invoice;
	}
   
	public Bordereau bordereauDtotoBordereau(BordereauInputDto bordereauDto) {
		
		TypeMap<BordereauInputDto, Bordereau> barToFooMapping = (modelMapper.getTypeMap(BordereauInputDto.class, Bordereau.class) == null) ? 
				modelMapper.createTypeMap(BordereauInputDto.class, Bordereau.class) : 
					modelMapper.getTypeMap(BordereauInputDto.class, Bordereau.class);
		
	    barToFooMapping.addMappings(mapping -> {
	    	mapping.using(toStringDate).map(BordereauInputDto::getTreatmentDate, Bordereau::setTreatmentDate);
	    });
	        		
		return modelMapper.map(bordereauDto, Bordereau.class);
	}
	
	public PaymentDetail paymentDetailDtotoPaymentDetail(PaymentDetailDto paymentDetailDto) {
		
		TypeMap<PaymentDetailDto, PaymentDetail> paymentDetailMapping = (modelMapper.getTypeMap(PaymentDetailDto.class, PaymentDetail.class) == null) ? 
				modelMapper.createTypeMap(PaymentDetailDto.class, PaymentDetail.class) : 
					modelMapper.getTypeMap(PaymentDetailDto.class, PaymentDetail.class);
				paymentDetailMapping.addMappings(mapping -> {
		    	mapping.using(toStringDate).map(PaymentDetailDto::getIssueDate, PaymentDetail::setIssueDate);
		    	mapping.map(PaymentDetailDto::getTransactionNumber, PaymentDetail::setTransactionNumber);
		    });
			
		return modelMapper.map(paymentDetailDto, PaymentDetail.class);
	}
	public List<PaymentDetail> paymentDetailsDtotoPaymentDetails(List<PaymentDetailDto> paymentDetailsDto) {
		
		return paymentDetailsDto.stream()
        .map(entity -> paymentDetailDtotoPaymentDetail(entity)).collect(Collectors.toList());
	}
	
	
	public Payment paymentDtotoPayment(PaymentInputDto paymentDto) {
		
		// TypeMap<PaymentInputDto, Payment> paymentMapping = 
				if (modelMapper.getTypeMap(PaymentInputDto.class, Payment.class) == null)  
					modelMapper.createTypeMap(PaymentInputDto.class, Payment.class)
						.addMappings(new PropertyMap<PaymentInputDto, Payment>() {
						protected void configure() {
								skip().setId(0);
								skip().setInvoice(null);
								skip().setPaymentDetails(null);
								
						}
					});
		
				
				
					// modelMapper.getTypeMap(PaymentInputDto.class, Payment.class);
		
				// modelMapper.addMappings();
//			paymentMapping.addMappings(mapping -> {
//	    	//mapping.map(PaymentInputDto::getPaymentDetails, Payment::setPaymentDetails);
//				// mapping.using(toDateString).map(PaymentDetailDto::getIssueDate, PaymentDetail::setIssueDate);
//	    });
		
		Payment payment = modelMapper.map(paymentDto, Payment.class);
//		payment.setPaymentDetails(paymentDto.getPaymentDetails().stream()
//	        .map(entity -> paymentDetailDtotoPaymentDetail(entity)).collect(Collectors.toList()));
	    if (!payment.getPaymentDetails().isEmpty())	payment.getPaymentDetails().clear();
		return payment;
	}
	
	
	public PaymentOutputDetailDto paymentDetailtoPaymentDetailDto(PaymentDetail paymentDetail) {
		
		TypeMap<PaymentDetail, PaymentOutputDetailDto> paymentDetailMapping = (modelMapper.getTypeMap(PaymentDetail.class, PaymentOutputDetailDto.class) == null) ? 
				modelMapper.createTypeMap(PaymentDetail.class, PaymentOutputDetailDto.class) : 
					modelMapper.getTypeMap(PaymentDetail.class, PaymentOutputDetailDto.class);
				paymentDetailMapping.addMappings(mapping -> {
		    	mapping.using(toDateString).map(PaymentDetail::getIssueDate, PaymentOutputDetailDto::setIssueDate);
		    	mapping.map(PaymentDetail::getTransactionNumber, PaymentOutputDetailDto::setTransactionNumber);
		    });
			
		return modelMapper.map(paymentDetail, PaymentOutputDetailDto.class);
	}
	
	public PaymentOutputDto paymentToPaymentOutputDto(Payment payment) {
		
		TypeMap<Payment, PaymentOutputDto> paymentOutputDto = modelMapper.getTypeMap(Payment.class, PaymentOutputDto.class); 
		if (paymentOutputDto == null)  {
			paymentOutputDto = modelMapper.createTypeMap(Payment.class, PaymentOutputDto.class);
			paymentOutputDto.addMappings(new PropertyMap<Payment, PaymentOutputDto>() {
					protected void configure() {
							skip().setInvoice(null);
							skip().setPaymentDetails(null);
							
					}
				});
			}
		
		PaymentOutputDto output = modelMapper.map(payment, PaymentOutputDto.class);
		// if (!output.getPaymentDetails().isEmpty())	output.getPaymentDetails().clear();
		payment.getPaymentDetails().forEach(entity -> {
			PaymentOutputDetailDto paymentDetailOutput = paymentDetailtoPaymentDetailDto(entity);
			output.addPaymentDetail(paymentDetailOutput);
		});
		
//		output.setPaymentDetails(payment.getPaymentDetails().stream()
//		        .map(entity -> paymentDetailtoPaymentDetailDto(entity)).collect(Collectors.toList()));
//		
		//		paymentOutputDto.PaymentO(payment.getPaymentDetails().stream()
//        .map(entity -> paymentDetailtoPaymentDetailDto(entity)).collect(Collectors.toList()));
//
//		paymentOutputDto.addMappings(mapping -> {
//	    	mapping.using(toDateString).map(Payment::getTreatmentDate, PaymentOutputDto::setTreatmentDate);
//	    	mapping.map(Payment::getPaymentDetails, PaymentOutputDto::setPaymentDetails);
//	    	
//	    });
	    return output;
	}

	public BordereauDto bordereauToBordereauDto(Bordereau bordereau) {
		
		TypeMap<Bordereau, BordereauDto> bordereauDto = (modelMapper.getTypeMap(Bordereau.class, BordereauDto.class) == null) ? 
				modelMapper.createTypeMap(Bordereau.class, BordereauDto.class) : modelMapper.getTypeMap(Bordereau.class, BordereauDto.class);
		
			bordereauDto.addMappings(mapping -> {
	    	mapping.using(toDateString).map(Bordereau::getTreatmentDate, BordereauDto::setTreatmentDate);
	    	mapping.map(Bordereau::getBordereauDetails, BordereauDto::setBordereauDetailList);
	    	
	    });
	    return modelMapper.map(bordereau, BordereauDto.class);
	}
	

	@Transactional
	public BordereauDetail bordereaudetailsDtotoBordereauDetails(BordereauDetailDto bordereauDetailDto) {
		
		BordereauDetail bordereauDetail = modelMapper.map(bordereauDetailDto, BordereauDetail.class);
		bordereauDetail.setProduct(productRepository.getProductByUID(bordereauDetailDto.getProductUid()));
		
		// new UID
		System.out.println("#############   generate UID bordereau detail ############################ ");
		UUID uuid = UUID.randomUUID();
		bordereauDetail.setUid(uuid.toString());

		bordereauDetail.setId(null);
		return bordereauDetail;
	}

	public List<BordereauDetail> bordereaudetailsDtotoBordereauDetailsList(List<BordereauDetailDto> bordereauDetailsDto) {
		System.out.println("####### ---> EntityDTOMapper - bordereaudetailsDtotoBordereauDetailsList ");
		
		return bordereauDetailsDto.stream()
        .map(entity -> bordereaudetailsDtotoBordereauDetails(entity)).collect(Collectors.toList());
	}

	@Transactional
	public Bordereau bordereauDtotoBordereau(BordereauUidDto bordereauDto, Invoice invoice) {
		
		Bordereau bordereau = bordereauRepository.getBordereauByUID(bordereauDto.getBordereauUid());
		
		bordereau.setInvoice(invoice);
		// new UID
		System.out.println("#############   generate UID bordereau detail ############################ ");
		bordereauRepository.save(bordereau);
		invoice.addBordereau(bordereau); // @TODO to verify
		return bordereau;
	}
	public List<Bordereau> bordereauDtotoBordereauList(List<BordereauUidDto> bordereauDto, Invoice invoice) {
		System.out.println("####### ---> EntityDTOMapper - bordereauDtotoBordereauList ");
		
		return bordereauDto.stream()
        .map(entity -> bordereauDtotoBordereau(entity, invoice)).collect(Collectors.toList());
	}
	
	public Contact contactDtotoContact(ContactInputDto contactDto) {
		return modelMapper.map(contactDto, Contact.class);
	}

	public List<ContactInputDto> contactDtotoContacts(List<Contact> entities) {
		return modelMapper.map(entities, new TypeToken<List<ContactInputDto>>() {
		}.getType());
	}

	public Contract contractDtotoContract(ContractInputDto contractDto) {
		return modelMapper.map(contractDto, Contract.class);
	}

	public Reduction reductionDtotoReduction(ReductionDto reductionDto) {
		return modelMapper.map(reductionDto, Reduction.class);
	}

	public List<Reduction> reductionDtotoReductions(List<ReductionDto> reductionDtoList) {
//		modelMapper.addMappings(new PropertyMap<ReductionDto, Reduction>() {
//			protected void configure() {
//				skip().setId(0);
//
//			}
//		});
		// modelMapper.createTypeMap(ReductionDto.class, Reduction.class)
		// .addMapping(ReductionDto::getProductId, Reduction::setProduct)
		// .addMapping(ReductionDto::getContractId, Reduction::setContract);
		return modelMapper.map(reductionDtoList, new TypeToken<List<Reduction>>() {
		}.getType());
	}

	public List<Bordereau> toBordereauDTOList(List<BordereauInputDto> bordereauInputDto) {

		List<Bordereau> customerDTOs = modelMapper.map(bordereauInputDto, new TypeToken<List<Bordereau>>() {
		}.getType());
		return customerDTOs;
	}

	public Product productDtotoProduct(ProductInputDto productDto) {
		return modelMapper.map(productDto, Product.class);
	}
	
	public Customer customerDtotoCustomer(CustomerInputDto customerDto) {
		return modelMapper.map(customerDto, Customer.class);
	}


	static class contractToContractDTOMapping extends PropertyMap<Contract, ContractInputDto> {
		// /** Define mapping configure PropertyMap <Source, Destination> **/
		@Override
		protected void configure() {
			map().setName(source.getName());
			skip().setReductions(null);
		}
		// map().setCustomerNum(source.getCustNum());
		// map().setTrustInvestorName(source.getName());
		// map().setAddressStreet(source.getStreet());
		// map().setAddressCity(source.getCity());
		// map().setAddressCountry(source.getCountry());
		// map().setDateOfBirth(source.getDob());
		// skip().setReturnCode(null);
	}

	static class productDTOToProductMapping extends PropertyMap<ProductInputDto, Product> {

		@Override
		protected void configure() {
			map().setCategory(source.getCategory());
		}
	}
	
	static class customerDTOtoCustomerMapping extends PropertyMap<CustomerInputDto, Customer> {

		@Override
		protected void configure() {
			map().setAdditionalAddress(source.getAdditionalAddress());
			map().setPostalCode(source.getPostalCode());
			map().setSiret(source.getSiret());
		}
	}

	static class productToProductDTOMapping extends PropertyMap<Product, ProductInputDto> {

		@Override
		protected void configure() {
			map().setCategory(source.getCategory());
		}
	}

//	static class reductionDtotoReductionsMapping extends PropertyMap<ReductionDto, Reduction> {
//
//		private ProductRepository productRepository;
//
//		@Autowired
//		public reductionDtotoReductionsMapping(ProductRepository productRepository) {
//			this.productRepository = productRepository;
//		}
//
//		@Override
//		protected void configure() {
//
//			// Product produit = productRepository.getOne(source.getProductId());
//			map().setProduct(productRepository.getProductByUID(source.getProductUid()));
//			skip().setId(null);
//		}
//
//	}
	
//	static class bordereauDetailDtotoBordereauDetailsMapping extends PropertyMap<BordereauDetailDto, BordereauDetail> {
//
//		private ProductRepository productRepository;
//
//		@Autowired
//		public bordereauDetailDtotoBordereauDetailsMapping(ProductRepository productRepository) {
//			this.productRepository = productRepository;
//		}
//
//		@Override
//		protected void configure() {
//
//			// Product produit = productRepository.getOne(source.getProductId());
//			map().setProduct(productRepository.getOne(source.getProductId()));
//			map().setPercentage(source.getPercentage());
//			map().setQte(source.getQte());
//			skip().setTotalCommandLine(null);
//			skip().setId(null);
//		}
//
//	}
}
