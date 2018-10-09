package com.services.direct.service;

import java.util.List;

import com.services.direct.bean.Invoice;
import com.services.direct.data.InvoiceInputDto;

public interface InvoiceService {

	Invoice getInvoiceById(Integer contactId);
	
	Invoice addInvoice(InvoiceInputDto invoiceDto);
	
	List<Invoice> getAllInvoices();
	
	void deleteInvoice(Integer id);
	
}
