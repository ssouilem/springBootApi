package com.services.direct.api;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import com.services.direct.bean.Company;
import com.services.direct.service.CompanyService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/companies")
@Api(value="onlinestore", description="API REST companies")
public class CompanyController {
	
	private CompanyService companyService;
	 
	@Autowired
	public CompanyController(final CompanyService companyService){
	  this.companyService = companyService;
	}
	
	// Get All Companies
    @ApiOperation(value = "View a list of available companyService",response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
	@RequestMapping(value = "/", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<Company>> getAllCompanies() {
// 		return this.companyService.getAllCompanies();
		return new ResponseEntity<>(companyService.getAllCompanies(), HttpStatus.OK);
	}

    // Get a Single company
	@RequestMapping(value = "/{UID}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Company> getCompanyByUID(@PathVariable(value = "UID") String companyUid) {
        
		Company company = companyService.getCompanyByUID(companyUid);
		if (company != null) {
			return new ResponseEntity<>(company, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
    }
	
	// Create a new Company
	@RequestMapping(value = "/", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Company> CreatedCompany(@RequestBody Company company){
	
		Company companyEntity = this.companyService.addCompany(company);
		if (companyEntity != null) {
			return new ResponseEntity<>(companyEntity, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}
	
	// Update a Company
	@PutMapping("/{UID}")
	public Company updateNote(@PathVariable(value = "UID") String companyUid, @Valid @RequestBody Company company) {
		return this.companyService.updateCompany(companyUid, company);
	}
	
	// Delete a Company
	@DeleteMapping("/{UID}")
	public ResponseEntity<Void> deleteCompanyByUID(@PathVariable("UID") String companyUid) {
		companyService.deleteCompanyByUID(companyUid);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}	
	
	// add a contract
	@ResponseBody
    @RequestMapping(value = "/{UID}/contract", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Company> attachContract(@PathVariable("UID") String companyUid, @RequestParam("contractUid") String contractUid ) {
		Company company = companyService.attachContract(companyUid, contractUid);
		if (company != null) {
			return new ResponseEntity<>(company, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}	
	
	
}
