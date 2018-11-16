package com.services.direct.service.impl;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.services.direct.bean.Bordereau;
import com.services.direct.bean.BordereauDetail;
import com.services.direct.bean.Customer;
import com.services.direct.data.BordereauInputDto;
import com.services.direct.exception.BusinessException;
import com.services.direct.exception.FileNotFoundException;
import com.services.direct.mapping.EntityDTOMapper;
import com.services.direct.repo.BordereauDetailRepository;
import com.services.direct.repo.BordereauRepository;
import com.services.direct.repo.CustomerRepository;
import com.services.direct.service.BordereauService;
import com.services.direct.utility.ResourceNotFoundException;
import com.services.direct.utility.Util;


@Service("bordereauService")
@Transactional
public class BordereauServiceImpl implements BordereauService {
	
	private static Logger log = LoggerFactory.getLogger(CustomerServiceImpl.class);
	
	@Autowired
	private EntityDTOMapper entityDTOMapper;
	
	private BordereauRepository bordereauRepository;
	
	private BordereauDetailRepository bordereauDetailRepository;
	
	private CustomerRepository customerRepository;
	
	@Autowired
	public BordereauServiceImpl(final BordereauRepository bordereauRepository, 
			final CustomerRepository customerRepository,
			final BordereauDetailRepository bordereauDetailRepository,
			EntityDTOMapper entityDTOMapper) { 
		this.bordereauRepository = bordereauRepository;
		this.bordereauDetailRepository = bordereauDetailRepository;
		this.customerRepository = customerRepository;
		this.entityDTOMapper     = entityDTOMapper;
	}
	
	@Transactional
	public Bordereau getBordereauByUID(String bordereauUid) {
		return bordereauRepository.getBordereauByUID(bordereauUid);
	}

	@Override
	@Transactional
	public Bordereau createBordereau(BordereauInputDto bordereauDto) throws BusinessException {

		log.info("bordereauDto -> dateStr : {}" + bordereauDto.getTreatmentDate());

		Bordereau bordereau = entityDTOMapper.bordereauDtotoBordereau(bordereauDto);

		// Associer la societe a votre bordereau
//		if (!customerRepository.existsById(bordereauDto.getCustomer())) {
//			throw new ResourceNotFoundException("CustomerId " + bordereauDto.getCustomer() + " not found");
//		} else {

			Customer customer = customerRepository.getCustomerByUID(bordereauDto.getCustomer());

			log.info("Customer already exist in DB : {}" + customer.getName());
			bordereau.setCustomer(customer);
			
			// add UID
			UUID uuid = UUID.randomUUID();
			bordereau.setUid(uuid.toString());
			
			// save bordereau
			this.bordereauRepository.save(bordereau);

			// lister les bordereaux details
			if (bordereauDto.getBordereauDetailList() != null) {
				List<BordereauDetail> bordereauDetails = entityDTOMapper
						.bordereaudetailsDtotoBordereauDetailsList(bordereauDto.getBordereauDetailList());
				bordereauDetails.forEach(bordereaudetail -> {
					log.info("== Bordeeau detail information : {} ", bordereaudetail.getProduct());
				});
				if (bordereauDetails != null && bordereauDetails.size() != 0) {

					bordereauDetails.forEach(bordereauDetail -> {
						bordereauDetail.setBordereau(bordereau);
						
						try {
							Double totalCommand = Util.totalCommandCalulate(bordereauDetail);
							bordereauDetail.setTotalCommandLine(totalCommand);
							
							// add totalCommandLine to subTotal
							Double subTotalBordereau = bordereau.getSubTotal() + totalCommand;
							bordereau.setSubTotal(subTotalBordereau);
							
						} catch (BusinessException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						// save
						bordereauDetailRepository.save(bordereauDetail);
					});

					bordereau.setBordereauDetails(bordereauDetails);
					return bordereau;

				} else {
					throw new FileNotFoundException("Error persist resource bordereauDetail", "FILE_NOT_FOUND");
				}

			} else {
				log.info(" =================== Pas bordereau details associés =================== ");
				throw new FileNotFoundException("Error JSON : Pas des bordereauDetails associés au bordereau",
						"INTERNAL_ERROR");
			}
		// }
	}

	
	

	@Override
	public Bordereau updateBordereau(Bordereau bordereau) {
		
		return this.bordereauRepository.save(bordereau);
	}

	@Override
	public List<Bordereau> getAllBordereaux() {
		List<Bordereau> bordereaux = (List<Bordereau>) bordereauRepository.findAll();
		return bordereaux;
	}

	@Override
	@Transactional
	public void deleteBordereauByUID(String bordereauUid) {
		this.bordereauRepository.deleteBordereauByUID(bordereauUid);
		
	}

}
