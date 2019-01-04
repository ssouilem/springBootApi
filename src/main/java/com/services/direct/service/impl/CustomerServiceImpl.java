package com.services.direct.service.impl;

import java.util.List;
import java.util.UUID;

import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import com.errors.ErrorDto;
import com.services.direct.bean.Contract;
import com.services.direct.bean.Customer;
import com.services.direct.bean.Product;
import com.services.direct.bean.security.User;
import com.services.direct.data.CustomerInputDto;
import com.services.direct.exception.BusinessException;
import com.services.direct.exception.DuplicateEntityException;
import com.services.direct.exception.FileNotFoundException;
import com.services.direct.exception.TechnicalException;
import com.services.direct.mapping.EntityDTOMapper;
import com.services.direct.repo.ContractRepository;
import com.services.direct.repo.CustomerRepository;
import com.services.direct.service.CustomerService;

@Service("customerService")
@Transactional
public class CustomerServiceImpl implements CustomerService {

	private static Logger log = LoggerFactory.getLogger(CustomerServiceImpl.class);

	private CustomerRepository customerRepository;

	private ContractRepository contractRepository;
	
	
	@Autowired
	private EntityDTOMapper entityDTOMapper;

	@Autowired
	public CustomerServiceImpl(final CustomerRepository customerRepository, ContractRepository contractRepository, EntityDTOMapper entityDTOMapper) {
		this.customerRepository = customerRepository;
		this.contractRepository = contractRepository;
		this.entityDTOMapper     = entityDTOMapper;
	}

	@Override
	@Transactional
	public Customer getCustomerByUID(String customerUid) {
		return customerRepository.getCustomerByUID(customerUid);
	}

	@SuppressWarnings("unused")
	@Override
	@Transactional
	public Customer addCustomer(CustomerInputDto customerDto, User user) throws BusinessException {
		// add UID
		Customer customer = entityDTOMapper.customerDtotoCustomer(customerDto);
		UUID uuid = UUID.randomUUID();
		customer.setUid(uuid.toString());
		customer.setCompany(user.getCompany());
		
		try {
			if (customer !=null) {
				if (customer.getSiret().isEmpty()) {
					throw new BusinessException("CUSTOMER_ERROR")
						.add(new ErrorDto("SIRET_ERROR", "La description est vide"));
				} else if (customer.getName().isEmpty()) {
					throw new BusinessException("CUSTOMER_ERROR")
					.add(new ErrorDto("NAME_ERROR", "Le nom de client est vide"));
				} else if (customer.getPhoneNumber().isEmpty()) {
					throw new BusinessException("CUSTOMER_ERROR")
					.add(new ErrorDto("PHONE_NUMBER_ERROR", "Le numero de telephone est vide"));
				} else {
					// verification de doublon
					if (customerRepository.findBySiretOrName(customer.getSiret(), customer.getName()) == 0) {
						return this.customerRepository.save(customer);
					} else {
						throw new DuplicateEntityException("DUPLICATE_CUSTOMER")
						.add(new ErrorDto("DUPLICATE_CUSTOMER", "the customer exists in base"));
					}
				}
			} else {
			   throw new FileNotFoundException("CUSTOMER_NOT_FOUND");
			}
		} catch (NullPointerException ex) {
			throw new FileNotFoundException("INTERNAL_ERROR")
			.add(new ErrorDto("REQUEST_ERROR", ex.getMessage()));
		}
	}

	@Override
	@Transactional
	@TransactionalEventListener
	public List<Customer> getAllCustomers(Integer companyId) {
		//return customerRepository.findAll();
		List<Customer> customers = customerRepository.getAllCustomerByCompany(companyId);
		return customers;
	}

	@Override
	public Customer updateCustomer(String customerUid, Customer customerRequest) {

		Customer customer = customerRepository.getCustomerByUID(customerUid);
		customer.setAddress(customerRequest.getAddress());
		customer.setFaxNumber(customerRequest.getFaxNumber());
		customer.setName(customerRequest.getName());
		customer.setMail(customerRequest.getMail());
		customer.setAdditionalAddress(customerRequest.getAdditionalAddress());
		customer.setPostalCode(customerRequest.getPostalCode());
		customer.setCity(customerRequest.getCity());
		customer.setPhoneNumber(customerRequest.getPhoneNumber());
		customer.setSiret(customerRequest.getSiret());
		
		return customerRepository.save(customer);
		
	}

	@Override
	@Transactional
	public void deleteCustomerByUID(String customerUid) throws BusinessException {
		try {
			customerRepository.deleteCustomerByUID(customerUid);
		} catch (ConstraintViolationException e) {
			throw new BusinessException("the customer is used", "USED_CUSTOMER");
		} catch (Exception e) {
			throw new TechnicalException("Internal exception", "INTERNAL_ERROR");
		}
		
	}

	@Override
	@Transactional
	public Customer attachContract(String customerUid, String contractUid) {
		log.info("Liste de parametre en entré: id {}, contractId {}", customerUid, contractUid);
		// if(!customerRepository.existsById(customerUid)) {
		// throw new ResourceNotFoundException("CustomerId " + id + " not found");
		// } else if (!contractRepository.existsById(contractId)) {
		// throw new ResourceNotFoundException("productId " + contractId + " not
		// found");
		// } else {
		Contract contract = contractRepository.getContractByUID(contractUid);
		Customer customer = customerRepository.getCustomerByUID(customerUid);

		log.info("contract information : contract {} ", contract.toString());
		customer.setContract(contract);
		customerRepository.save(customer);

		return customer;
		// }
	}
}
