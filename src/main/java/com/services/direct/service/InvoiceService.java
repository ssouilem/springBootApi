package com.services.direct.service;

import java.util.List;

import com.services.direct.bean.Invoice;
import com.services.direct.bean.security.User;
import com.services.direct.data.InvoiceInputDto;
import com.services.direct.exception.BusinessException;

public interface InvoiceService {

	Invoice getInvoiceByUID(String invoiceUid);
	
	Invoice addInvoice(InvoiceInputDto invoiceDto, User user) throws BusinessException;
	
	List<Invoice> getAllInvoices(Integer companyId);
	
	void deleteInvoiceByUID(String invoiceUid);

	Invoice addBordereauToInvoice(Integer id, Integer bordereauId);
	
}
