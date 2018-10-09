package com.services.direct.service.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.services.direct.bean.Company;
import com.services.direct.bean.Invoice;
import com.services.direct.data.InvoiceInputDto;
import com.services.direct.repo.CompanyRepository;
import com.services.direct.repo.InvoiceRepository;
import com.services.direct.service.InvoiceService;


@Service("invoiceService")
@Transactional
public class InvoiceServiceImpl implements InvoiceService {
	
	@Autowired
	private ModelMapper modelMapper;
	
	private InvoiceRepository invoiceRepository;
	
	private CompanyRepository companyRepository;
	
	@Autowired
	public InvoiceServiceImpl(final InvoiceRepository invoiceRepository, final CompanyRepository companyRepository) { 
		this.invoiceRepository = invoiceRepository;
		this.companyRepository = companyRepository;
	}
	
	public Invoice getInvoiceById(Integer invoiceId) {
		return invoiceRepository.getOne(invoiceId);
	}

	@Override
	public Invoice addInvoice(InvoiceInputDto invoiceDto) {
		Invoice invoice = modelMapper.map(invoiceDto, Invoice.class);
		
		Integer companyId = invoiceDto.getCompany();
		Company company = companyRepository.getOne(companyId); 
		
		 if (company != null) {
	         System.out.println("Company already exist in DB : {}" + company.getName());
	         invoice.setCompany(company);
        } else {
        	// throw exception
        }
		return this.invoiceRepository.save(invoice);
	}

	@Override
	public List<Invoice> getAllInvoices() {
		List<Invoice> invoices = (List<Invoice>) invoiceRepository.findAll();
		return invoices;
	}

	@Override
	public void deleteInvoice(Integer id) {
		// TODO Auto-generated method stub
	}
	
	

}
