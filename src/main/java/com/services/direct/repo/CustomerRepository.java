package com.services.direct.repo;

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

	@Query("SELECT customer FROM Customer customer WHERE customer.id=(:id)")
	Customer getCustomerById(@Param("id") Integer customerId);
	
	@Query("SELECT customer FROM Customer customer WHERE customer.uid=(:uid)")
	Customer getCustomerByUID(@Param("uid") String customerUid);
	
	@Modifying
	@Query("DELETE FROM Customer customer WHERE customer.uid=(:uid)")
	void deleteCustomerByUID(@Param("uid") String customerUid);

}
