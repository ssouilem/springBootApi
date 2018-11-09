package com.services.direct.service;

import java.util.List;

import com.services.direct.bean.Contract;
import com.services.direct.bean.Reduction;
import com.services.direct.data.ContractInputDto;
import com.services.direct.data.ReductionDto;

public interface ContractService {

	Contract getContractByUID(String contractUid);
	
	Contract createContract(ContractInputDto contractDto) throws Exception;
	
	Contract updateContract(Integer contractId, ContractInputDto contractDto);
	
	List<Contract> getAllContracts();
	
	void deleteContract(Integer contractId);
	
	Reduction addReductionByProduct(String contractUid, ReductionDto reductionDto);
	
	Contract unlinkReductionOfContract(String contractUid, String reductionUid);
	
	void deleteContractByUID(String contractUid);
	
}
