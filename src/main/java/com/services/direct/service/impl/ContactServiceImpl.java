package com.services.direct.service.impl;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.services.direct.bean.Company;
import com.services.direct.bean.Contact;
import com.services.direct.data.ContactInputDto;
import com.services.direct.exception.BusinessException;
import com.services.direct.exception.ContactNotFoundException;
import com.services.direct.exception.FileNotFoundException;
import com.services.direct.mapping.EntityDTOMapper;
import com.services.direct.repo.CompanyRepository;
import com.services.direct.repo.ContactRepository;
import com.services.direct.service.ContactService;

@Service("contactService")
@Transactional
// @JsonIgnoreProperties({"JavassistLazyInitializer", "handler"})
public class ContactServiceImpl implements ContactService {

	private static Logger log = LoggerFactory.getLogger(ContactServiceImpl.class);

	@Autowired
	private EntityDTOMapper entityDTOMapper;

	private ContactRepository contactRepository;

	private CompanyRepository companyRepository;

	@Autowired
	public ContactServiceImpl(final ContactRepository contactRepository, 
			final CompanyRepository companyRepository,
			EntityDTOMapper entityDTOMapper) {
		
		this.contactRepository = contactRepository;
		this.companyRepository = companyRepository;
		this.entityDTOMapper   = entityDTOMapper;
	}

	@Override
	public Contact getContactByUID(String contactUid) throws BusinessException {
		
		Contact contact = contactRepository.getContactByUID(contactUid);
		if (contact != null) {
			return contact;
		} else {
			throw new ContactNotFoundException("The resource contact was not found", "FILE_NOT_FOUND");
		}
	}

	@Override
	@Transactional
	public Contact addContact(ContactInputDto contactDto) throws BusinessException {

		// Convert Dto to @Entity Contact
		Contact contact = entityDTOMapper.contactDtotoContact(contactDto);
		if (contact == null) {
			throw new ContactNotFoundException("The resource contcat was not found", "CONTACT_NOT_FOUND");
		}

		// Recuperation de la company
		String companyUid = contactDto.getCompany();
		Company company = companyRepository.getCompanyByUID(companyUid);

		// add company to contact
		if (company != null) {
			log.info("Company already exist in DB : {}", company.getName());
			contact.setCompany(company);
		} else {
			throw new FileNotFoundException("The resource company was not found BD", "FILE_NOT_FOUND");
		}
		// add UID
		UUID uuid = UUID.randomUUID();
		contact.setUid(uuid.toString());
		return this.contactRepository.save(contact);

	}

	/** get list of contacts
	 * 
	 * @return List of contact
	 * @throws BusinessException
	 * 
	 */
	@Override
	public List<Contact> getAllContacts() throws BusinessException {
		List<Contact> contacts = contactRepository.findAll();
		
		if (contacts == null) {
			throw new ContactNotFoundException("The resource contcat was not found", "CONTACT_NOT_FOUND");
        }
		log.info(" La liste de contat : " + contacts.toString());
		return contacts;
	}
	
	/**
	 * delete contact by id
	 * 
	 * @return void
	 * 
	 */

	@Override
	@Transactional
	public void deleteContactByUID(String contactUid) {
		this.contactRepository.deleteContactByUID(contactUid);
		
	}	

}
