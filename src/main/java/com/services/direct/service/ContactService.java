package com.services.direct.service;

import java.util.List;

import com.services.direct.bean.Contact;
import com.services.direct.data.ContactInputDto;
import com.services.direct.exception.BusinessException;

public interface ContactService {

	Contact getContactById(Integer contactId) throws BusinessException;
	
	Contact addContact(ContactInputDto contactDto) throws BusinessException;
	
	List<Contact> getAllContacts() throws BusinessException;
	
	void deleteContact(Integer id);
	
}
