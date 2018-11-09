package com.services.direct.service.impl;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.services.direct.bean.Bordereau;
import com.services.direct.bean.Company;
import com.services.direct.bean.Invoice;
import com.services.direct.data.InvoiceInputDto;
import com.services.direct.exception.BusinessException;
import com.services.direct.exception.FileNotFoundException;
import com.services.direct.mapping.EntityDTOMapper;
import com.services.direct.repo.BordereauRepository;
import com.services.direct.repo.CompanyRepository;
import com.services.direct.repo.InvoiceRepository;
import com.services.direct.service.InvoiceService;
import com.services.direct.utility.ResourceNotFoundException;


@Service("invoiceService")
@Transactional
public class InvoiceServiceImpl implements InvoiceService {
	
	private static Logger log = LoggerFactory.getLogger(InvoiceServiceImpl.class);
	@Autowired
	private EntityDTOMapper entityDTOMapper;
	
	private InvoiceRepository invoiceRepository;
	
	private BordereauRepository bordereauRepository;
	
	private CompanyRepository companyRepository;
	
	@Autowired
	public InvoiceServiceImpl(final InvoiceRepository invoiceRepository, 
			final CompanyRepository companyRepository,
			final BordereauRepository bordereauRepository,
			EntityDTOMapper entityDTOMapper) { 
		this.invoiceRepository = invoiceRepository;
		this.companyRepository = companyRepository;
		this.bordereauRepository = bordereauRepository;
		this.entityDTOMapper     = entityDTOMapper;
	}
	
	@Transactional
	public Invoice getInvoiceByUID(String invoiceUid) {
		return invoiceRepository.getInvoiceByUID(invoiceUid);
	}

	@Override
	@Transactional
	public Invoice addInvoice(InvoiceInputDto invoiceDto) throws BusinessException {
		
		log.info("addInvoice -> Invoice Number : {}" + invoiceDto.getNumber());
		Invoice invoice = entityDTOMapper.invoiceDtotoInvoice(invoiceDto);
		
		// Associer la societe a votre bordereau
		if (!companyRepository.existsById(invoiceDto.getCompany())) {
			throw new ResourceNotFoundException("CompanyId " + invoiceDto.getCompany() + " not found");
		} else {

			Company company = companyRepository.getOne(invoiceDto.getCompany());
			log.info("Company already exist in DB : {}" + company.getName());
			invoice.setCompany(company);
			
			
			// controler et lister les bordereaux
			if (invoiceDto.getBordereaux() == null || invoiceDto.getBordereaux().isEmpty()) {
				throw new FileNotFoundException("Ressource bordereauList not found", "FILE_NOT_FOUND");
			} else {
				invoiceDto.getBordereaux().forEach(bordereauId -> {

					// verifier au niveau_ de la base isExist
					if (bordereauRepository.existsById(bordereauId)) {
						// add to List
						Bordereau bordereau = bordereauRepository.getBordereauById(bordereauId);
						bordereau.setInvoice(invoice);
						bordereauRepository.save(bordereau);
						invoice.addBordereau(bordereau);
						
					} else {
						try {
							throw new FileNotFoundException("Bordereau not found", "FILE_NOT_FOUND");
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
				 
				// add UID
				UUID uuid = UUID.randomUUID();
				invoice.setUid(uuid.toString());
				
				// calulate InvoiceTotal

				// save
				invoiceRepository.save(invoice);
			}
		}
		return invoice;
	}
	
	@Override
	@Transactional
	public Invoice addBordereauToInvoice(Integer id, Integer bordereauId) {
			log.info("Liste de parametre en entré: id {}, bordereauId {}", id, bordereauId);
			if(!invoiceRepository.existsById(id)) {
	            throw new ResourceNotFoundException("Invoice " + id + " not found");
	        } else if (!bordereauRepository.existsById(bordereauId)) {
	        	throw new ResourceNotFoundException("bordereauId " + bordereauId + " not found");
	        } else {
	        	Bordereau bordereau = bordereauRepository.getBordereauById(bordereauId);
	        	Invoice invoice  = invoiceRepository.getOne(id);
	        	bordereau.setInvoice(invoice);
	        	bordereauRepository.save(bordereau);
	        	invoice.getBordereaux().add(bordereau);
	        	
	        	return invoice;
	        }
		}

	@Override
	public List<Invoice> getAllInvoices() {
		List<Invoice> invoices = (List<Invoice>) invoiceRepository.findAll();
		return invoices;
	}

	@Override
	@Transactional
	public void deleteInvoiceByUID(String invoiceUid) {
		this.invoiceRepository.deleteInvoiceByUID(invoiceUid);
		
	}
	
}
