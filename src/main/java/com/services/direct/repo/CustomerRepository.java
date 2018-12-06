package com.services.direct.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.services.direct.bean.Customer;

@Repository
@Transactional
public interface CustomerRepository extends JpaRepository<Customer, Integer>{

	
	@Query("SELECT customer FROM Customer customer " +
			//"LEFT JOIN FETCH customer.company AS company " +
			"WHERE customer.company.id=(:companyId)")
	List<Customer> getAllCustomerByCompany(@Param("companyId") Integer companyId);
	
	@Query("SELECT customer FROM Customer customer WHERE customer.id=(:id)")
	Customer getCustomerById(@Param("id") Integer customerId);
	
	@Query("SELECT customer FROM Customer customer WHERE customer.uid=(:uid)")
	Customer getCustomerByUID(@Param("uid") String customerUid);
	
	@Modifying
	@Query("DELETE FROM Customer customer WHERE customer.uid=(:uid)")
	void deleteCustomerByUID(@Param("uid") String customerUid);

	@Query("SELECT count(*) FROM Customer customer WHERE (customer.siret=(:siret) or customer.name=(:name))")
	int findBySiretOrName(@Param("siret") String siret, @Param("name") String name);
	
}
