package com.services.direct.service.impl;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.services.direct.bean.Company;
import com.services.direct.bean.Contract;
import com.services.direct.repo.CompanyRepository;
import com.services.direct.repo.ContractRepository;
import com.services.direct.service.CompanyService;
import com.services.direct.utility.ResourceNotFoundException;

@Service("companyService")
@Transactional
public class CompanyServiceImpl implements CompanyService {
	
	private static Logger log = LoggerFactory.getLogger(CompanyServiceImpl.class);

	private CompanyRepository companyRepository;
	
	private ContractRepository contractRepository;
	
	@Autowired
	public CompanyServiceImpl(final CompanyRepository companyRepository,
			ContractRepository contractRepository) {
		this.companyRepository = companyRepository;
		this.contractRepository = contractRepository;
	}
	
	@Override
	@Transactional
	public Company getCompanyByUID(String companyUid) {
		return companyRepository.getCompanyByUID(companyUid);
	}

	@Override
	@Transactional
	public Company addCompany(Company company) {
		// add UID
		UUID uuid = UUID.randomUUID();
		company.setUid(uuid.toString());
		return this.companyRepository.save(company);

	}

	@Override
	public List<Company> getAllCompanies() {
		return companyRepository.findAll();
	}
	
	@Override
	public Company updateCompany(String companyUid, Company companyRequest) {
		
//		if(!companyRepository.existsById(companyUid)) {
//            throw new ResourceNotFoundException("CompanyId " + companyUid + " not found");
//      }
		
		Company company = companyRepository.getCompanyByUID(companyUid);
	 	company.setAddress(companyRequest.getAddress());
	 	company.setFaxNumber(companyRequest.getFaxNumber());
        return companyRepository.save(company);
	        
	}
	
	@Override
	@Transactional
	public void deleteCompanyByUID(String companyUid) {
		
//		if(!companyRepository.existsById(id)) {
//            throw new ResourceNotFoundException("CompanyId " + id + " not found");
//        }
		companyRepository.deleteCompanyByUID(companyUid);
	}

	@Override
	@Transactional
	public Company attachContract(String companyUid, String contractUid) {
		log.info("Liste de parametre en entré: id {}, contractId {}", companyUid, contractUid);
//		if(!companyRepository.existsById(companyUid)) {
//            throw new ResourceNotFoundException("CompanyId " + id + " not found");
//        } else if (!contractRepository.existsById(contractId)) {
//        	throw new ResourceNotFoundException("productId " + contractId + " not found");
//        } else {
        	Contract contract = contractRepository.getContractByUID(contractUid);
        	Company company  = companyRepository.getCompanyByUID(companyUid);
        	
        	
        	log.info("contract information : contract {} ", contract.toString());
        	companyRepository.save(company);
        	
        	return company;
       // }
	}
}
