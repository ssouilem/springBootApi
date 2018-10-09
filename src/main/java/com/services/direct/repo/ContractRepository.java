package com.services.direct.repo;

import org.springframework.data.jpa.repository.JpaRepository;
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

}
