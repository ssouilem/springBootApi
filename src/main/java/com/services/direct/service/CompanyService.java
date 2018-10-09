package com.services.direct.service;

import java.util.List;

import com.services.direct.bean.Company;

public interface CompanyService {

	Company getCompanyId(Integer contactId);
	
	void addCompany(Company contact);
	
	List<Company> getAllCompanies();
	
	Company updateCompany(Integer id, Company cp);
	
	void deleteCompany(Integer articleId);

	Company attachContract(Integer id, Integer contractId);
	
}
