package com.services.direct.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.services.direct.bean.Bordereau;
import com.services.direct.bean.BordereauDetail;
import com.services.direct.bean.Company;
import com.services.direct.data.BordereauInputDto;
import com.services.direct.exception.BusinessException;
import com.services.direct.exception.FileNotFoundException;
import com.services.direct.mapping.EntityDTOMapper;
import com.services.direct.repo.BordereauDetailRepository;
import com.services.direct.repo.BordereauRepository;
import com.services.direct.repo.CompanyRepository;
import com.services.direct.service.BordereauService;
import com.services.direct.utility.ResourceNotFoundException;


@Service("bordereauService")
@Transactional
public class BordereauServiceImpl implements BordereauService {
	
	private static Logger log = LoggerFactory.getLogger(CompanyServiceImpl.class);
	
	@Autowired
	private EntityDTOMapper entityDTOMapper;
	
	private BordereauRepository bordereauRepository;
	
	private BordereauDetailRepository bordereauDetailRepository;
	
	private CompanyRepository companyRepository;
	
	@Autowired
	public BordereauServiceImpl(final BordereauRepository bordereauRepository, 
			final CompanyRepository companyRepository,
			final BordereauDetailRepository bordereauDetailRepository,
			EntityDTOMapper entityDTOMapper) { 
		this.bordereauRepository = bordereauRepository;
		this.bordereauDetailRepository = bordereauDetailRepository;
		this.companyRepository = companyRepository;
		this.entityDTOMapper     = entityDTOMapper;
	}
	
	@Transactional
	public Bordereau getBordereauById(Integer bordereauId) {
		return bordereauRepository.getOne(bordereauId);
	}

	@Override
	public Bordereau createBordereau(BordereauInputDto bordereauDto) throws BusinessException {
		
		log.info("bordereauDto -> dateStr : {}" + bordereauDto.getTreatmentDate());
		Bordereau bordereau = entityDTOMapper.bordereauDtotoBordereau(bordereauDto);
		
		// Associer la societe a votre bordereau
		if(!companyRepository.existsById(bordereauDto.getCompany())) {
		    throw new ResourceNotFoundException("CompanyId " + bordereauDto.getCompany() + " not found");
        } else {
        	
        	Company company = companyRepository.getOne(bordereauDto.getCompany()); 

        	log.info("Company already exist in DB : {}" + company.getName());
        	bordereau.setCompany(company);
        	// save bordereau
        	this.bordereauRepository.save(bordereau);

        	// lister les bordereaux details
        	if (bordereauDto.getBordereauDetailList() != null) {
        		List<BordereauDetail> bordereauDetails = entityDTOMapper.bordereaudetailsDtotoBordereauDetailsList(bordereauDto.getBordereauDetailList());
        		bordereauDetails.forEach(bordereaudetail -> {
        			log.info("== Bordeeau detail information : {} ", bordereaudetail.getProduct());
        		});
        		if (bordereauDetails != null && bordereauDetails.size() != 0) {

        			bordereauDetails.forEach(bordereauDetail -> {
    					bordereauDetail.setBordereau(bordereau);
    					
    					// @TODO : controle totalCommandLine (calcul & compare)
    					
    					// add totalCommandLine to subTotal
    					
    					// save
    					bordereauDetailRepository.save(bordereauDetail);
    				});
        			
        			// calcule de subTotal bordereau
        			bordereau.setBordereauDetails(bordereauDetails);
        			return bordereau;
        			
    			} else {
    		        throw new FileNotFoundException("Error persist resource bordereauDetail", "INTERNAL_ERROR");
    			}
        		
        	} else {
        		log.info(" =================== Pas bordereau details associés =================== ");
        		throw new FileNotFoundException("Error JSON : Pas des bordereauDetails associés au bordereau", "INTERNAL_ERROR");
        	}    	
        }
		
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
	public void deleteBordereau(Integer id) {
		// TODO Auto-generated method stub
	}

}
