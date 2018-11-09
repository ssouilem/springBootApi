package com.services.direct.mapping;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
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
import com.services.direct.bean.Invoice;
import com.services.direct.bean.Product;
import com.services.direct.bean.Reduction;
import com.services.direct.data.BordereauDetailDto;
import com.services.direct.data.BordereauInputDto;
import com.services.direct.data.ContactInputDto;
import com.services.direct.data.ContractInputDto;
import com.services.direct.data.InvoiceInputDto;
import com.services.direct.data.ProductInputDto;
import com.services.direct.data.ReductionDto;
import com.services.direct.repo.ProductRepository;

@Component
public class EntityDTOMapper {

	ModelMapper modelMapper = new ModelMapper();

	private ProductRepository productRepository;

	@Autowired
	public EntityDTOMapper(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	public EntityDTOMapper() {
		System.out.println("#############   EntityDTOMapper ############################ ");
		modelMapper.getConfiguration().setAmbiguityIgnored(true);
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		modelMapper.addMappings(new PropertyMap<ContactInputDto, Contact>() {
			protected void configure() {
				// 'first name' is mapped automatically
				map().setFirstName(source.getFirstName());
				skip().setCompany(null);
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

		modelMapper.addMappings(new PropertyMap<InvoiceInputDto, Invoice>() {
			protected void configure() {
					skip().setId(0);
					skip().setBordereaux(null);
			}
		});
		
		// modelMapper.addMappings(new contractDTOToContractMapping());
		modelMapper.addMappings(new productDTOToProductMapping());
		modelMapper.addMappings(new productToProductDTOMapping());
		modelMapper.addMappings(new contractToContractDTOMapping());
		modelMapper.addMappings(new PropertyMap<ReductionDto, Reduction>() {
			protected void configure() {
					map().setProductUid(source.getProductUid());
					skip().setId(0);
					skip().setProduct(null);
			}
		});
		//modelMapper.addMappings(new reductionDtotoReductionsMapping(productRepository));
//		modelMapper.addMappings(new bordereauDetailDtotoBordereauDetailsMapping(productRepository));
		
		System.out.println("#############   EntityDTOMapper ############################ "
				+ modelMapper.getConfiguration().toString());
	}

//	Provider<Date> dateProvider = new AbstractProvider<Date>() {
//		@Override
//		public Date get() {
//			return new Date();
//		}
//	};
	
//	Converter<BordereauInputDto, Bordereau> converter = new Converter<BordereauInputDto, Bordereau>()
//    {
//        @Override
//        public Bordereau convert(MappingContext<BordereauInputDto, Bordereau> mappingContext) {
//
//        	BordereauInputDto candidate = mappingContext.getSource();
//        	Bordereau bordereau = new Bordereau();
//        	String dateFormat = "yyyy-MM-dd";
//        	
//        	Date date = new SimpleDateFormat(dateFormat).parse(candidate.getTreatmentDateStr());
//            
//
//            mappingContext.getDestination().setTreatmentDate(date);
//
//            return bordereau;
//        }
//    };
//    modelMapper.createTypeMap(Candidate.class, CandidateDTO.class).setConverter(converter);
	
	
	
//	Converter<Date, Date> datetoDate = new AbstractConverter<Date, Date>() {
//		@Override
//		protected Date convert(Date source) {
//			String dateFormat = "yyyy-MM-dd";
//			try {
//				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//		        String dateInterviewConverted = df.format(source);
//				System.out.println("####### ---> EntityDTOMapper - DatetoDate " + dateInterviewConverted);
//				return new SimpleDateFormat(dateFormat).parse(dateInterviewConverted);
//			} catch (ParseException e) {
//				e.printStackTrace();
//				return null;
//			}
//
//		}
//	};
	
//	PropertyMap<BordereauInputDto, Bordereau> candidateMapping = new PropertyMap<BordereauInputDto, Bordereau>()
//    {
//        protected void configure()
//        {
//            System.out.println("####### ---> EntityDTOMapper - candidateMapping ");
//        	// to map these two attributes, they will use the corresponding converters
//            using(toStringDate).map(source.getTreatmentDate()).setTreatmentDate(null);
//            
//            
//        }
//    };
	
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
		
//		modelMapper.addMappings(new PropertyMap<BordereauInputDto, Bordereau>() {
//			protected void configure() {
//				
//			
//				Converter<String, Date> toStringDate = new AbstractConverter<String, Date>() {
//					@Override
//					protected Date convert(String source) {
//						String dateFormat = "yyyy-MM-dd";
//						try {
//							return new SimpleDateFormat(dateFormat).parse(source);
//						} catch (ParseException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//							return null;
//						}
//
//					}
//				};
//				using(toStringDate).map(source.getTreatmentDate(), destination.getTreatmentDate());
//				
//			}
//		});

//		modelMapper.createTypeMap(String.class, Date.class);
//		modelMapper.addConverter(toStringDate);
//		modelMapper.getTypeMap(String.class, Date.class).setProvider(dateProvider);
// 		add the mapping settings to the ModelMapper
		
		System.out.println("####### ---> EntityDTOMapper - bordereauDtotoBordereau ");
	    // modelMapper.addMappings(candidateMapping);
		

		TypeMap<BordereauInputDto, Bordereau> barToFooMapping = (modelMapper.getTypeMap(BordereauInputDto.class, Bordereau.class) == null) ? 
				modelMapper.createTypeMap(BordereauInputDto.class, Bordereau.class) : 
					modelMapper.getTypeMap(BordereauInputDto.class, Bordereau.class);
		
	    barToFooMapping.addMappings(mapping -> {
	    	mapping.using(toStringDate).map(BordereauInputDto::getTreatmentDate, Bordereau::setTreatmentDate);
	    });
	    
	    
	    //barToFooMapping.addMappings(candidateMapping);
	    // barToFooMapping.addMappings(mapping -> mapping.using(toStringDate).skip(destinationSetter);
		
//	    		
		return modelMapper.map(bordereauDto, Bordereau.class);
	}

	@Transactional
	public BordereauDetail bordereaudetailsDtotoBordereauDetails(BordereauDetailDto bordereauDetailDto) {
		
		BordereauDetail bordereauDetail = modelMapper.map(bordereauDetailDto, BordereauDetail.class);
		bordereauDetail.setProduct(productRepository.getProductByUID(bordereauDetailDto.getProductUid()));
		bordereauDetail.setId(null);
		return bordereauDetail;
	}

	public List<BordereauDetail> bordereaudetailsDtotoBordereauDetailsList(List<BordereauDetailDto> bordereauDetailsDto) {
		System.out.println("####### ---> EntityDTOMapper - bordereaudetailsDtotoBordereauDetailsList ");
		
		return bordereauDetailsDto.stream()
        .map(entity -> bordereaudetailsDtotoBordereauDetails(entity)).collect(Collectors.toList());
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
			map().setQuality(source.getQuality());
		}
	}

	static class productToProductDTOMapping extends PropertyMap<Product, ProductInputDto> {

		@Override
		protected void configure() {
			map().setQuality(source.getQuality());
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
