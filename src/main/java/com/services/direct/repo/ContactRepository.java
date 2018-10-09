package com.services.direct.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.services.direct.bean.Contact;

@Repository
@Transactional
public interface ContactRepository extends JpaRepository<Contact, Integer>{

	@Query("SELECT contact FROM Contact contact WHERE contact.id=(:id)")
	Contact getContactId(@Param("id") Integer contactId);
	
	@Query("SELECT contact FROM Contact contact")
	List<Contact> getAllContacts();
}
