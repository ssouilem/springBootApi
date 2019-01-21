package com.services.direct.api;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.services.direct.bean.Invoice;
import com.services.direct.bean.security.User;
import com.services.direct.data.BordereauDetailPdf;
import com.services.direct.data.InvoiceInputDto;
import com.services.direct.data.output.PdfViwerOutput;
import com.services.direct.exception.BusinessException;
import com.services.direct.pdf.InvoiceServicePdf;
import com.services.direct.service.ICityService;
import com.services.direct.service.InvoiceService;
import com.services.direct.utility.Nombre;
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

       
		List<BordereauDetailPdf> bordereauDetails = new ArrayList<BordereauDetailPdf>();
        invoice.getBordereaux().forEach(bordereau -> {
        	bordereau.getBordereauDetails().forEach(details -> {
        		BordereauDetailPdf bdDetail = new BordereauDetailPdf(details.getPercentage(), 
        															 details.getDescription(), 
        															 details.getQte(), 
        															 details.getProduct().getPrice(), 
        															 details.getTotalCommandLine());
        		bordereauDetails.add(bdDetail);
        	});
        });

	        
	    OrderModel order = new OrderModel(invoice.getNumber(), invoice.getCustomer(), user.getCompany(), bordereauDetails);
	    order.setAmount(invoice.getAmount());
	    order.setCreatedDate(invoice.getCreatedDate());
	    order.setIssueDate(invoice.getIssueDate());
	    order.setOtherExpenses(invoice.getOtherExpenses());
	    order.setRemarks(invoice.getRemarks());
	    order.setDiplayTotalInLetter(invoice.getSumInLetter());
	    
	    try {
		    String amountTTCString = order.getAmountTTC();
		    Double amountTTC = Double.parseDouble(amountTTCString.toString());
		    
			String amountInLetter;
			amountInLetter = Nombre.CALCULATE.getValue(amountTTC,".");
			order.setAmountInLetter(amountInLetter);
			System.out.println(amountTTC);
			System.out.println(amountInLetter);
			
	    } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//  order.setAmountInLetter();
	   
	   
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
	@RequestMapping(value = "/preview", method = RequestMethod.POST) //, produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<PdfViwerOutput> pdfPreview(@RequestBody InvoiceInputDto previewDto,  Authentication authentication, @AuthenticationPrincipal User user) throws IOException, BusinessException {

		log.info("Auth user : {}", user);
		Invoice invoice = this.invoiceService.convertDtoEntity(previewDto);
		
		List<BordereauDetailPdf> bordereauDetails = new ArrayList<BordereauDetailPdf>();
        invoice.getBordereaux().forEach(bordereau -> {
        	bordereau.getBordereauDetails().forEach(details -> {
        		BordereauDetailPdf bdDetail = new BordereauDetailPdf(details.getPercentage(), 
        															 details.getDescription(), 
        															 details.getQte(), 
        															 details.getProduct().getPrice(), 
        															 details.getTotalCommandLine());
        		bordereauDetails.add(bdDetail);
        	});
        });

	        
	    OrderModel order = new OrderModel(invoice.getNumber(), invoice.getCustomer(), user.getCompany(), bordereauDetails);
	    order.setAmount(invoice.getAmount());
	    order.setCreatedDate(invoice.getCreatedDate());
	    order.setIssueDate(invoice.getIssueDate());
	   
	    order.setOtherExpenses(invoice.getOtherExpenses());
	    order.setRemarks(invoice.getRemarks());
	    order.setDiplayTotalInLetter(invoice.getSumInLetter());
	    
	    try {
		    String amountTTCString = order.getAmountTTC();
		    Double amountTTC = Double.parseDouble(amountTTCString.toString());
		    
			String amountInLetter;
			amountInLetter = Nombre.CALCULATE.getValue(amountTTC,".");
			order.setAmountInLetter(amountInLetter);
			System.out.println(amountTTC);
			System.out.println(amountInLetter);
			
	    } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    UUID uuid = UUID.randomUUID();
		invoice.setUid(uuid.toString());
		String filenamePreview= uuid.toString().concat("-preview");
	   
	    File filename = invoiceServicePdf.generatePdfFor(filenamePreview, order, Locale.FRANCE);
	    

	    if (filename.exists()) {
	    	PdfViwerOutput output = new PdfViwerOutput(filename.getName(), "application/pdf", filename.length());
	    	return new ResponseEntity<>(output, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}	
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
