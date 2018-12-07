package com.services.direct.service;

import java.util.List;

import com.services.direct.bean.Contact;
import com.services.direct.bean.security.User;
import com.services.direct.data.ContactInputDto;
import com.services.direct.exception.BusinessException;

public interface ContactService {

	Contact getContactByUID(String contactUid) throws BusinessException;
	
	Contact addContact(ContactInputDto contactDto, User user) throws BusinessException;
	
	void deleteContactByUID(String contactUid);

	List<Contact> getAllContacts(Integer companyId) throws BusinessException;
	
}
