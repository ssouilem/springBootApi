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

import com.services.direct.bean.Bordereau;
import com.services.direct.bean.BordereauDetail;
import com.services.direct.data.BordereauDetailDto;
import com.services.direct.data.BordereauInputDto;
import com.services.direct.exception.BusinessException;
import com.services.direct.service.BordereauDetailService;
import com.services.direct.service.BordereauService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/bordereau")
@Api(value="onlinestore", description="API REST bordereau")
public class BordereauController {
	
	private BordereauService bordereauService;
	
	private BordereauDetailService bordereaudetailService;
	
	 
	@Autowired
	public BordereauController(final BordereauService bordereauService, final BordereauDetailService bordereauDetailService){
	  this.bordereauService = bordereauService;
	  this.bordereaudetailService = bordereauDetailService;
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
	public List<Bordereau> getAllContacts() {
		return this.bordereauService.getAllBordereaux();
	}

	@RequestMapping(value = "/{bordereauId}", method = RequestMethod.GET)
    @ResponseBody
    public Bordereau getInvoiceById(@PathVariable final Integer bordereauId) {
        return this.bordereauService.getBordereauById(bordereauId);
    }
	
	 
	@RequestMapping(value = "/", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Bordereau> createBordereau(@RequestBody BordereauInputDto bordereauDto) throws BusinessException{
		Bordereau bordereau = this.bordereauService.createBordereau(bordereauDto);
		if (bordereau != null) {
			return new ResponseEntity<>(bordereau, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}	
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.OK)
    public void deleteShop(@PathVariable Integer id) {
        bordereauService.deleteBordereau(id);
    }
	
    @ResponseBody
    @RequestMapping(value = "/{Id}/addBordereauDetail", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Bordereau> addBordereauDetail(@PathVariable final Integer bordereauId, @RequestBody BordereauDetailDto bordereauDetailDto) {
    	BordereauDetail bordereauDetail = this.bordereaudetailService.addBordereauDetail(bordereauDetailDto);
    	if (bordereauDetail != null && bordereauDetail.getId() != null) {

    		Bordereau bordereau = this.bordereauService.getBordereauById(bordereauId);
    		if (bordereau != null) {
    			bordereau.addBordereauDetail(bordereauDetail);
    			this.bordereauService.updateBordereau(bordereau);
    			return new ResponseEntity<>(bordereau, HttpStatus.CREATED);
    		} else { 
    			// throw exception
    			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    		}
    	} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}	
    }
    
    @ResponseBody
    @RequestMapping(value = "/{Id}/removeBordereauDetail/{bordereauDetailId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Bordereau> removeBordereauDetail(@PathVariable(name = "Id") final Integer bordereauId, @PathVariable(name = "bordereauDetailId") final Integer bordereauDetailId) {
    	Bordereau bordereau = this.bordereauService.getBordereauById(bordereauId);

    	if (bordereau != null) {
    		this.bordereaudetailService.deleteBordereauDetail(bordereauDetailId);
    		return new ResponseEntity<>(bordereau, HttpStatus.OK);
		} else { 
			// throw exception
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
    }
}
