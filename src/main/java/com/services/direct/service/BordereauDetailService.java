package com.services.direct.service;

import java.util.List;

import com.services.direct.bean.BordereauDetail;
import com.services.direct.data.BordereauDetailDto;
import com.services.direct.exception.BusinessException;

public interface BordereauDetailService {

	BordereauDetail getBordereauDetailByUID(String bordereauDetailUid);
	
	List<BordereauDetail> getAllBordereauDetails();
	
	void deleteBordereauDetailByUID(String bordereauDetailUid);

	BordereauDetail addBordereauDetailByBordereau(String bordereauUid, BordereauDetailDto bordereauDetailDto) throws BusinessException;


}
