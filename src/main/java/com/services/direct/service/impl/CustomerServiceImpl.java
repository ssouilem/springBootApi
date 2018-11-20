package com.services.direct.service.impl;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.services.direct.bean.Contract;
import com.services.direct.bean.Customer;
import com.services.direct.bean.Product;
import com.services.direct.data.CustomerInputDto;
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

	@Override
	@Transactional
	public Customer addCustomer(CustomerInputDto customerDto) {
		// add UID
		Customer customer = entityDTOMapper.customerDtotoCustomer(customerDto);
		UUID uuid = UUID.randomUUID();
		customer.setUid(uuid.toString());
		return this.customerRepository.save(customer);

	}

	@Override
	public List<Customer> getAllCompanies() {
		return customerRepository.findAll();
	}

	@Override
	public Customer updateCustomer(String customerUid, Customer customerRequest) {

		// if(!customerRepository.existsById(customerUid)) {
		// throw new ResourceNotFoundException("CustomerId " + customerUid + " not
		// found");
		// }

		Customer customer = customerRepository.getCustomerByUID(customerUid);
		customer.setAddress(customerRequest.getAddress());
		customer.setFaxNumber(customerRequest.getFaxNumber());
		return customerRepository.save(customer);

	}

	@Override
	@Transactional
	public void deleteCustomerByUID(String customerUid) {

		// if(!customerRepository.existsById(id)) {
		// throw new ResourceNotFoundException("CustomerId " + id + " not found");
		// }
		customerRepository.deleteCustomerByUID(customerUid);
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
