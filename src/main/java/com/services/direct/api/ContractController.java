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

import com.services.direct.bean.Contract;
import com.services.direct.bean.Reduction;
import com.services.direct.data.ContractInputDto;
import com.services.direct.data.ReductionDto;
import com.services.direct.service.ContractService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/contract")
@Api(value="onlinestore", description="API REST contract")
public class ContractController {
	
	private ContractService contractService;
	 
	@Autowired
	public ContractController(final ContractService contractService){
	  this.contractService = contractService;
	}
	
	@ApiOperation(value = "Create new contract",response = Iterable.class)
	@RequestMapping(value = "/", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Contract> createContract(@RequestBody ContractInputDto contractDto) throws Exception {
		Contract contract = this.contractService.createContract(contractDto);
		if (contract != null) {
			return new ResponseEntity<>(contract, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}	
	}
	
	@ApiOperation(value = "View a list of available contract",response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
	@RequestMapping(value = "/", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<Contract>> getAllContracts() {
		List<Contract> contracts = this.contractService.getAllContracts();
		if (contracts != null && !contracts.isEmpty()) {
			return new ResponseEntity<>(contracts, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}	
	}

	@RequestMapping(value = "/{UID}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Contract> getContractByUID(@PathVariable("UID") final String contractId) {
        Contract contract =  this.contractService.getContractByUID(contractId);
        if (contract != null) {
			return new ResponseEntity<>(contract, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}	
    }
	
	@RequestMapping(value="/{UID}", method=RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.OK)
    public void deleteContractByUID(@PathVariable("UID") String contractUid) {
        contractService.deleteContractByUID(contractUid);
    }
	
    @ResponseBody
    @RequestMapping(value = "/{UID}/reductions", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Reduction> addReductionByProduct(@PathVariable("UID") final String contractUid, @RequestBody ReductionDto reductionDto) {
    	
    	Reduction reduction = this.contractService.addReductionByProduct(contractUid, reductionDto);
    	if (reduction != null && reduction.getId() != null) {
    		return new ResponseEntity<>(reduction, HttpStatus.CREATED);
    	} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}	
    }
    
    @ResponseBody
    @RequestMapping(value = "/{UID}/reductions/{reductionUid}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Contract> unlinkReductionOfContract(@PathVariable("UID") final String contractUid, @PathVariable(name = "reductionUid") final String reductionUid) {
    		
    	Contract contract = this.contractService.unlinkReductionOfContract(contractUid, reductionUid);
    	return new ResponseEntity<>(contract, HttpStatus.OK);
		
    }
}
