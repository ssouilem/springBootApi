package com.services.direct.service;

import java.util.List;

import com.services.direct.bean.Bordereau;
import com.services.direct.data.BordereauInputDto;
import com.services.direct.data.output.BordereauDto;
import com.services.direct.exception.BusinessException;

public interface BordereauService {

	Bordereau getBordereauByUID(String bordereauUid);
	
	Bordereau createBordereau(BordereauInputDto bordereauDto) throws BusinessException;
	
	List<BordereauDto> getAllBordereaux();
	
	void deleteBordereauByUID(String bordereauUid);

	Bordereau updateBordereau(Bordereau bordereau);
	
}
