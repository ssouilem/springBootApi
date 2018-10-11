package com.services.direct.service;

import java.util.List;

import com.services.direct.bean.BordereauDetail;
import com.services.direct.data.BordereauDetailDto;
import com.services.direct.exception.BusinessException;

public interface BordereauDetailService {

	BordereauDetail getBordereauDetailById(Integer brDetailId);
	
	List<BordereauDetail> getAllBordereauDetails();
	
	void deleteBordereauDetail(Integer brDetailId);

	BordereauDetail addBordereauDetailByBordereau(Integer bordereauId, BordereauDetailDto bordereauDetailDto) throws BusinessException;


}
