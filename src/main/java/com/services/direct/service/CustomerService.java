package com.services.direct.service;

import java.util.List;

import com.services.direct.bean.Customer;
import com.services.direct.bean.security.User;
import com.services.direct.data.CustomerInputDto;
import com.services.direct.exception.BusinessException;

public interface CustomerService {

	Customer getCustomerByUID(String customerUid);
	
	// Customer addCustomer(CustomerInputDto contact) throws BusinessException;
	
	List<Customer> getAllCustomers(Integer companyId);
	
	Customer updateCustomer(String customerUid, Customer cp);
	
	void deleteCustomerByUID(String customerUid) throws BusinessException;

	Customer attachContract(String customerUid, String contractUid);

	Customer addCustomer(CustomerInputDto customerDto, User user) throws BusinessException;
	
}
