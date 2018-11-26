package com.services.direct.api;

import java.util.List;

import javax.validation.Valid;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.services.direct.bean.Customer;
import com.services.direct.data.CustomerInputDto;
import com.services.direct.exception.BusinessException;
import com.services.direct.exception.ErrorMessage;
import com.services.direct.service.CustomerService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/customers")
@Api(value="onlinestore", description="API REST customer")
public class CustomerController {
	
	private CustomerService customerService;
	 
	@Autowired
	public CustomerController(final CustomerService customerService){
	  this.customerService = customerService;
	}
	
	// Get All Companies
    @ApiOperation(value = "View a list of available customerService",response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
	@RequestMapping(value = "/", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<Customer>> getAllCompanies() {
// 		return this.customerService.getAllCompanies();
		return new ResponseEntity<>(customerService.getAllCompanies(), HttpStatus.OK);
	}

    // Get a Single customer
	@RequestMapping(value = "/{UID}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Customer> getCustomerByUID(@PathVariable(value = "UID") String customerUid) {
        
		Customer customer = customerService.getCustomerByUID(customerUid);
		if (customer != null) {
			return new ResponseEntity<>(customer, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
    }
	
	// Create a new Customer
	@RequestMapping(value = "/", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Customer> CreatedCustomer(@RequestBody CustomerInputDto customer) throws BusinessException{
	
		Customer customerEntity = this.customerService.addCustomer(customer);
		if (customerEntity != null) {
			return new ResponseEntity<>(customerEntity, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}
	
	// Update a Customer
	@PutMapping("/{UID}")
	public Customer updateNote(@PathVariable(value = "UID") String customerUid, @Valid @RequestBody Customer customer) {
		return this.customerService.updateCustomer(customerUid, customer);
	}
	
	// Delete a Customer
	@DeleteMapping("/{UID}")
	@CrossOrigin
	public ResponseEntity<ErrorMessage> deleteCustomerByUID(@PathVariable("UID") String customerUid) {
		
		try {
			customerService.deleteCustomerByUID(customerUid);
		} catch (BusinessException ex) {
			if (ex.getCause() instanceof ConstraintViolationException) {
				ResponseEntity<ErrorMessage> responseEntity = new ResponseEntity<ErrorMessage>(new ErrorMessage("Database error" + ex.getCause()), new HttpHeaders(),
						HttpStatus.CONFLICT);
				return responseEntity;
			}
			return new ResponseEntity<ErrorMessage>(new ErrorMessage("Database error" + ex.getMessage()), new HttpHeaders(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}	
	
	// add a contract
	@ResponseBody
    @RequestMapping(value = "/{UID}/contract", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Customer> attachContract(@PathVariable("UID") String customerUid, @RequestParam("contractUid") String contractUid ) {
		Customer customer = customerService.attachContract(customerUid, contractUid);
		if (customer != null) {
			return new ResponseEntity<>(customer, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}	
	
	
}
