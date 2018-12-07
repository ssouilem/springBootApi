package com.services.direct.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.services.direct.bean.Invoice;
import com.services.direct.bean.security.User;
import com.services.direct.data.InvoiceInputDto;
import com.services.direct.exception.BusinessException;
import com.services.direct.service.InvoiceService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/invoices")
@Api(value="onlinestore", description="API REST invoices")
public class InvoiceController {
	
	private InvoiceService invoiceService;
	
	 
	@Autowired
	public InvoiceController(final InvoiceService invoiceService){
	  this.invoiceService = invoiceService;
	}
	
    @ApiOperation(value = "View a list of available invoice",response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
	@RequestMapping(value = "/", method = RequestMethod.GET)
	@ResponseBody
	public List<Invoice> getAllInvoices(@AuthenticationPrincipal User user) {
		return this.invoiceService.getAllInvoices(user.getCompany().getId());
	}

	@RequestMapping(value = "/{UID}", method = RequestMethod.GET)
    @ResponseBody
    public Invoice getInvoiceById(@PathVariable("UID") final String invoiceUid) {
        return this.invoiceService.getInvoiceByUID(invoiceUid);
    }
	
	 
	@RequestMapping(value = "/", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Invoice> addInvoice(@RequestBody InvoiceInputDto contactDto, @AuthenticationPrincipal User user) throws BusinessException{
		Invoice invoice = this.invoiceService.addInvoice(contactDto, user);
		if (invoice != null) {
			return new ResponseEntity<>(invoice, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}	
	}
	
	@ResponseBody
    @RequestMapping(value = "/{Id}/bordereau", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Invoice> addBordereauToInvoice(@PathVariable("UID") Integer id, @RequestParam("bordereauId") Integer bordereauId, @AuthenticationPrincipal User user ) {
		Invoice invoice = invoiceService.addBordereauToInvoice(id, bordereauId);
		if (invoice != null) {
			return new ResponseEntity<>(invoice, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}	
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.OK)
    public void deleteInvoiceByUID(@PathVariable("UID") String invoiceUid, @AuthenticationPrincipal User user) {
		invoiceService.deleteInvoiceByUID(invoiceUid);
    }
}
