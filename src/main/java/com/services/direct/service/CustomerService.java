package com.services.direct.service;

import java.util.List;

import com.services.direct.bean.Customer;
import com.services.direct.data.CustomerInputDto;

public interface CustomerService {

	Customer getCustomerByUID(String customerUid);
	
	Customer addCustomer(CustomerInputDto contact);
	
	List<Customer> getAllCompanies();
	
	Customer updateCustomer(String customerUid, Customer cp);
	
	void deleteCustomerByUID(String customerUid);

	Customer attachContract(String customerUid, String contractUid);
	
}
