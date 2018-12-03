package com.services.direct.api;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.services.direct.bean.BordereauDetail;
import com.services.direct.bean.Invoice;
import com.services.direct.pdf.InvoiceServicePdf;
import com.services.direct.service.ICityService;
import com.services.direct.service.InvoiceService;
import com.stackextend.generatepdfdocument.model.OrderModel;


@RestController
@RequestMapping("/invoice")
public class PdfController {
	
	@Autowired
	ICityService cityService;
	
	@Autowired
	private InvoiceService invoiceService;
	
	@Resource
    private InvoiceServicePdf invoiceServicePdf;

	@RequestMapping(value = "/{UID}/pdfreport", method = RequestMethod.GET) //, produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<InputStreamResource> citiesReport(@PathVariable("UID") final String invoiceUid) throws IOException {

		Invoice invoice = this.invoiceService.getInvoiceByUID(invoiceUid);

       
		 List<BordereauDetail> bordereauDetails = new ArrayList<BordereauDetail>();
	        invoice.getBordereaux().forEach(bordereau -> {
	        	bordereau.getBordereauDetails().forEach(details -> {
	        		bordereauDetails.add(details);
	        	});
	        });

	        
	    OrderModel order = new OrderModel(invoice.getNumber(), invoice.getCustomer(), bordereauDetails);
	   
	   
	    File filename = invoiceServicePdf.generatePdfFor(order, Locale.FRANCE);



		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "inline; filename=citiesreport.pdf");
		InputStream stream = new FileInputStream(filename);

		return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF)
				.body(new InputStreamResource(stream));
	}
}
