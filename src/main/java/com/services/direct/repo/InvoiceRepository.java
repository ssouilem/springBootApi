package com.services.direct.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.services.direct.bean.Invoice;

@Repository
@Transactional
public interface InvoiceRepository extends JpaRepository<Invoice, Integer>{

	@Query("SELECT invoice FROM Invoice invoice WHERE invoice.uid=(:uid)")
	Invoice getInvoiceByUID(@Param("uid") String invoiceUid);
	
	@Modifying
	@Query("DELETE FROM Invoice invoice WHERE invoice.uid=(:uid)")
	void deleteInvoiceByUID(@Param("uid") String invoiceUid);
	
}
