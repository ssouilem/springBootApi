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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.services.direct.bean.Payment;
import com.services.direct.bean.security.User;
import com.services.direct.data.PaymentInputDto;
import com.services.direct.data.output.PaymentOutputDto;
import com.services.direct.exception.BusinessException;
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
	public List<Payment> getAllPayments(@AuthenticationPrincipal User user) {
		return this.paymentService.getAllPayments(user.getCompany().getId());
	}

	@RequestMapping(value = "/{UID}", method = RequestMethod.GET)
    @ResponseBody
    public Payment getPaymentById(@PathVariable("UID") final String paymentUid, @AuthenticationPrincipal User user) {
        return this.paymentService.getPaymentByUID(paymentUid);
    }
	
	 
	@RequestMapping(value = "/", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<PaymentOutputDto> addPayment(@RequestBody PaymentInputDto paymentDto, @AuthenticationPrincipal User user) throws BusinessException{
		PaymentOutputDto payment = this.paymentService.addPayment(paymentDto, user);
		
		if (payment != null) {
			return new ResponseEntity<>(payment, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}	
	}
	
	@RequestMapping(value="/{UID}", method=RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.OK)
    public void deletePayment(@PathVariable("UID") final String paymentUid, @AuthenticationPrincipal User user) {
        paymentService.deletePaymentByUID(paymentUid);
    }
}
