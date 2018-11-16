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
import com.services.direct.data.BordereauDetailDto;
import com.services.direct.exception.BusinessException;
import com.services.direct.exception.FileNotFoundException;
import com.services.direct.mapping.EntityDTOMapper;
import com.services.direct.repo.BordereauDetailRepository;
import com.services.direct.repo.BordereauRepository;
import com.services.direct.service.BordereauDetailService;
import com.services.direct.utility.Util;


@Service("bordereauDetailService")
@Transactional
public class BordereauDetailServiceImpl implements BordereauDetailService {
	
	private static Logger log = LoggerFactory.getLogger(BordereauDetailServiceImpl.class);
	
	@Autowired
	private EntityDTOMapper entityDTOMapper;
	
	private BordereauDetailRepository bordereauDetailRepository;
	
	private BordereauRepository bordereauRepository;
	
	@Autowired
	public BordereauDetailServiceImpl(final BordereauDetailRepository bordereauDetailRepository, 
			final BordereauRepository bordereauRepository,
			EntityDTOMapper entityDTOMapper) { 
		this.bordereauDetailRepository = bordereauDetailRepository;
		this.bordereauRepository = bordereauRepository;
		this.entityDTOMapper = entityDTOMapper; 
	}

	@Override
	public BordereauDetail getBordereauDetailByUID(String bordereauDetailUid) {
		return bordereauDetailRepository.getBordereauDetailByUID(bordereauDetailUid);
	}

	@Override
	public List<BordereauDetail> getAllBordereauDetails() {
		List<BordereauDetail> bordereauDetails = (List<BordereauDetail>) bordereauDetailRepository.findAll();
		return bordereauDetails;
	}

	@Transactional
	public BordereauDetail addBordereauDetailByBordereau(String bordereauUid, BordereauDetailDto bordereauDetailDto) throws BusinessException {
		
		BordereauDetail bordereauDetail = entityDTOMapper.bordereaudetailsDtotoBordereauDetails(bordereauDetailDto);
		
		if (bordereauDetail != null) {
			Bordereau bordereau = this.bordereauRepository.getBordereauByUID(bordereauUid);
			if (bordereau != null) {
				bordereauDetail.setBordereau(bordereau);
				UUID uuid = UUID.randomUUID();
				bordereauDetail.setUid(uuid.toString());
				log.info(" ****** Entity bordereau ID {} " , bordereau.getUid());
				try {
					Double totalCommand = Util.totalCommandCalulate(bordereauDetail);
					bordereauDetail.setTotalCommandLine(totalCommand);
					
					Double subTotalBordereau = bordereau.getSubTotal() + totalCommand;
					bordereau.setSubTotal(subTotalBordereau);
					
					
				} catch (BusinessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				bordereauDetailRepository.save(bordereauDetail);
				
				return bordereauDetail;
				
			} else {
				throw new FileNotFoundException("The resource bordereau was not found", "FILE_NOT_FOUND");
			}
		} else {
			throw new FileNotFoundException("The resource bordereaudetail was not found", "FILE_NOT_FOUND");
		}
	}
		

	@Override
	@Transactional
	public void deleteBordereauDetailByUID(String productUid) {
		this.bordereauDetailRepository.deleteBordereauDetailByUID(productUid);
		
	}
	
}