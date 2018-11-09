package com.services.direct.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.services.direct.bean.Contract;

@Repository
@Transactional
public interface ContractRepository extends JpaRepository<Contract, Integer>{
	
	@Query("SELECT contract FROM Contract contract WHERE contract.id=(:id)")
	Contract getContractId(@Param("id") Integer contractId);
	
	@Query("SELECT contract FROM Contract contract WHERE contract.uid=(:uid)")
	Contract getContractByUID(@Param("uid") String contractUid);
	
	@Modifying
	@Query("DELETE FROM Contract contract WHERE contract.uid=(:uid)")
	void deleteContractByUID(@Param("uid") String contactUid);

}
