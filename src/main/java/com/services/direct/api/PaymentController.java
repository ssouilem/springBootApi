package com.services.direct.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.services.direct.bean.Payment;
import com.services.direct.data.PaymentInputDto;
import com.services.direct.service.PaymentService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/payment")
@Api(value="onlinestore", description="API REST payment")
public class PaymentController {
	
	private PaymentService paymentService;
	
	 
	@Autowired
	public PaymentController(final PaymentService paymentService){
	  this.paymentService = paymentService;
	}
	
    @ApiOperation(value = "View a list of available payment",response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
	@RequestMapping(value = "/", method = RequestMethod.GET)
	@ResponseBody
	public List<Payment> getAllPayments() {
		return this.paymentService.getAllPayments();
	}

	@RequestMapping(value = "/{Id}", method = RequestMethod.GET)
    @ResponseBody
    public Payment getPaymentById(@PathVariable final Integer paymentId) {
        return this.paymentService.getPaymentById(paymentId);
    }
	
	 
	@RequestMapping(value = "/", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Payment> addBordereau(@RequestBody PaymentInputDto paymentDto){
		Payment payment = this.paymentService.addPayment(paymentDto);
		if (payment != null) {
			return new ResponseEntity<>(payment, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}	
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.OK)
    public void deletePayment(@PathVariable Integer id) {
        paymentService.deletePayment(id);
    }
}
