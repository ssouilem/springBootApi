package com.services.direct.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.services.direct.bean.Invoice;

@Repository
@Transactional
public interface InvoiceRepository extends JpaRepository<Invoice, Integer>{

//	@Query("SELECT contact FROM Contact contact WHERE contact.id=(:id)")
//	Contact getContactId(@Param("id") Integer contactId);
//	
//	@Query("SELECT contact FROM Contact contact")
//	List<Contact> getAllContacts();
}
