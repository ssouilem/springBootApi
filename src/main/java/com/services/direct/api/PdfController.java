package com.services.direct.api;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.services.direct.bean.BordereauDetail;
import com.services.direct.bean.Invoice;
import com.services.direct.bean.security.User;
import com.services.direct.pdf.InvoiceServicePdf;
import com.services.direct.service.ICityService;
import com.services.direct.service.InvoiceService;
import com.stackextend.generatepdfdocument.model.OrderModel;


@RestController
@RequestMapping("/invoice")
public class PdfController {
	
	private static Logger log = LoggerFactory.getLogger(PdfController.class);
	
	@Autowired
	ICityService cityService;
	
	@Autowired
	private InvoiceService invoiceService;
	
	@Resource
    private InvoiceServicePdf invoiceServicePdf;

	@CrossOrigin
	@RequestMapping(value = "/{UID}/pdfreport", method = RequestMethod.GET, produces = "application/pdf") //, produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<InputStreamResource> citiesReport(@PathVariable("UID") final String invoiceUid, Authentication authentication, @AuthenticationPrincipal User user) throws IOException {

		log.info("Auth user : {}", user.getCompany().getId());
		Invoice invoice = this.invoiceService.getInvoiceByUID(invoiceUid);

       
		 List<BordereauDetail> bordereauDetails = new ArrayList<BordereauDetail>();
	        invoice.getBordereaux().forEach(bordereau -> {
	        	bordereau.getBordereauDetails().forEach(details -> {
	        		bordereauDetails.add(details);
	        		bordereauDetails.add(details);
	        		bordereauDetails.add(details);
	        	});
	        });

	        
	    OrderModel order = new OrderModel(invoice.getNumber(), invoice.getCustomer(), user.getCompany(), bordereauDetails);
	    order.setAmount(invoice.getAmount());
	    order.setCreatedDate(invoice.getCreatedDate());
	    order.setIssueDate(invoice.getIssueDate());
	   
	   
	    File filename = invoiceServicePdf.generatePdfFor(invoiceUid, order, Locale.FRANCE);



		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType("application/pdf"));
		headers.setContentLength(filename.length());
		headers.setContentDispositionFormData("attachment", filename.getName());
		headers.add("Content-Disposition", "attachment; filename="+ filename.getName());
		InputStream stream = new FileInputStream(filename);

		return ResponseEntity
				.ok()
				.headers(headers)
				.contentType(MediaType.parseMediaType("application/octet-stream"))
				.body(new InputStreamResource(stream));
	}
	
	@CrossOrigin
	@RequestMapping(value = "/getpdf/{pdf}", method = RequestMethod.GET, produces = "application/pdf")
	public ResponseEntity<InputStreamResource> getPdf(@PathVariable("pdf") String fileName) throws IOException {

	    try {
	        File file = new File("C:\\Users\\ssouilem\\AppData\\Local\\Temp\\"+ fileName+ ".pdf");

	        if (file.exists()) {
	            // here I use Commons IO API to copy this file to the response output stream, I don't know which API you use.
	            // FileUtils.copyFile(file, response.getOutputStream());

	            // here we define the content of this file to tell the browser how to handle it
	            HttpHeaders headers = new HttpHeaders();
	    		headers.setContentType(MediaType.parseMediaType("application/pdf"));
	    		headers.setContentLength(file.length());
	    		headers.setContentDispositionFormData("attachment", fileName + ".pdf");
	    		//headers.add("Content-Disposition", "attachment; filename=" + fileName + ".pdf");
	    		
	            InputStreamResource isr = new InputStreamResource(new FileInputStream(file));
	            return new ResponseEntity<InputStreamResource>(isr, headers, HttpStatus.OK);
	            
	        } else {
	            System.out.println("Contract Not Found");
	            return new ResponseEntity<InputStreamResource>(HttpStatus.NOT_FOUND);
	        }
	    } catch (IOException exception) {
	        System.out.println("Contract Not Found");
	        System.out.println(exception.getMessage());
	        return new ResponseEntity<InputStreamResource>(HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
}
