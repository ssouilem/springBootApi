package com.services.direct.service.impl;

import java.util.List;

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

@Service
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
	public Company getCompanyId(Integer companyId) {
		return companyRepository.getOne(companyId);
	}

	@Override
	public void addCompany(Company company) {
		this.companyRepository.save(company);

	}

	@Override
	public List<Company> getAllCompanies() {
		return companyRepository.findAll();
	}
	
	@Override
	public Company updateCompany(Integer id, Company companyRequest) {
		
		if(!companyRepository.existsById(id)) {
            throw new ResourceNotFoundException("CompanyId " + id + " not found");
        }
		
		 return companyRepository.findById(id).map(company -> {
			 	company.setAddress(companyRequest.getAddress());
			 	company.setFaxNumber(companyRequest.getFaxNumber());
	            return companyRepository.save(company);
	        }).orElseThrow(() -> new ResourceNotFoundException("CompanyId " + id + "not found"));
	}
	
	@Override
	public void deleteCompany(Integer id) {
		
		if(!companyRepository.existsById(id)) {
            throw new ResourceNotFoundException("CompanyId " + id + " not found");
        }
		companyRepository.deleteById(id);
	}

	@Override
	@Transactional
	public Company attachContract(Integer id, Integer contractId) {
		log.info("Liste de parametre en entré: id {}, contractId {}", id, contractId);
		if(!companyRepository.existsById(id)) {
            throw new ResourceNotFoundException("CompanyId " + id + " not found");
        } else if (!contractRepository.existsById(contractId)) {
        	throw new ResourceNotFoundException("productId " + contractId + " not found");
        } else {
        	Contract contract = contractRepository.getContractId(contractId);
        	Company company  = companyRepository.getOne(id);
        	
        	
        	log.info("contract information : contract {} ", contract.toString());
        	company.setContract(contract);
        	companyRepository.save(company);
        	
        	return company;
        }
	}

}
