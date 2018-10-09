package com.services.direct.service;

import java.util.List;

import com.services.direct.bean.Bordereau;
import com.services.direct.data.BordereauInputDto;
import com.services.direct.exception.BusinessException;

public interface BordereauService {

	Bordereau getBordereauById(Integer bordereauId);
	
	Bordereau createBordereau(BordereauInputDto bordereauDto) throws BusinessException;
	
	List<Bordereau> getAllBordereaux();
	
	void deleteBordereau(Integer id);

	Bordereau updateBordereau(Bordereau bordereau);
	
}
