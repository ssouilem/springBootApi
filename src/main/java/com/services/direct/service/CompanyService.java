package com.services.direct.service;

import java.util.List;

import com.services.direct.bean.Company;

public interface CompanyService {

	Company getCompanyByUID(String companyUid);
	
	Company addCompany(Company contact);
	
	List<Company> getAllCompanies();
	
	Company updateCompany(String companyUid, Company cp);
	
	void deleteCompanyByUID(String companyUid);

	Company attachContract(String companyUid, String contractUid);
	
}
