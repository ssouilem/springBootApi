package com.services.direct.service.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.services.direct.bean.Bordereau;
import com.services.direct.bean.BordereauDetail;
import com.services.direct.data.BordereauDetailDto;
import com.services.direct.repo.BordereauDetailRepository;
import com.services.direct.repo.BordereauRepository;
import com.services.direct.service.BordereauDetailService;


@Service("bordereauDetailService")
@Transactional
public class BordereauDetailServiceImpl implements BordereauDetailService {
	
	@Autowired
	private ModelMapper modelMapper;
	
	private BordereauDetailRepository bordereauDetailRepository;
	
	private BordereauRepository bordereauRepository;
	
	@Autowired
	public BordereauDetailServiceImpl(final BordereauDetailRepository bordereauDetailRepository, final BordereauRepository bordereauRepository) { 
		this.bordereauDetailRepository = bordereauDetailRepository;
		this.bordereauRepository = bordereauRepository;
	}

	@Override
	public BordereauDetail getBordereauDetailById(Integer bordereaudetailId) {
		return bordereauDetailRepository.getOne(bordereaudetailId);
	}

	@Override
	public List<BordereauDetail> getAllBordereauDetails() {
		List<BordereauDetail> bordereauDetails = (List<BordereauDetail>) bordereauDetailRepository.findAll();
		return bordereauDetails;
	}


	@Override
	public BordereauDetail addBordereauDetail(BordereauDetailDto bordereauDetailDto) {
		BordereauDetail bordereauDetail = modelMapper.map(bordereauDetailDto, BordereauDetail.class);
		
		Integer bordereauId = null ;//bordereauDetailDto.getBordereau();
		Bordereau bordereau = bordereauRepository.getOne(bordereauId); 
		
		if (bordereau != null) {
	         System.out.println("Bordereau already exist in DB : {}" + bordereau.getNumber());
	         bordereauDetail.setBordereau(bordereau);
        } else {
        	// throw exception
        }
		return this.bordereauDetailRepository.save(bordereauDetail);
	}
	
	@Override
	public void deleteBordereauDetail(Integer brDetailId) {
		// TODO Auto-generated method stub
		
	}
	

}
