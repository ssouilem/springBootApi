package com.services.direct.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.services.direct.bean.Payment;

@Repository
@Transactional
public interface PaymentRepository extends JpaRepository<Payment, Integer>{

	@Query("SELECT payment FROM Payment payment WHERE payment.uid=(:uid)")
	Payment getPaymentByUID(@Param("uid") String paymentUid);
	
	@Modifying
	@Query("DELETE FROM Payment payment WHERE payment.uid=(:uid)")
	void deletePaymentByUID(@Param("uid") String paymentUid);

}
