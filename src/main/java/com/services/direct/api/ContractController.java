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
			return new ResponseEntity<>(contract, HttpStatus.OK);
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
	public List<Contract> getAllContracts() {
		return this.contractService.getAllContracts();
	}

	@RequestMapping(value = "/{Id}", method = RequestMethod.GET)
    @ResponseBody
    public Contract getContractById(@PathVariable final Integer contractId) {
        return this.contractService.getContractById(contractId);
    }
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.OK)
    public void deleteContract(@PathVariable Integer id) {
        contractService.deleteContract(id);
    }
	
    @ResponseBody
    @RequestMapping(value = "/{contractId}/reductions", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Reduction> addReductionByProduct(@PathVariable final Integer contractId, @RequestBody ReductionDto reductionDto) {
    	Reduction reduction = this.contractService.addReductionByProduct(contractId, reductionDto);
    	
    	
    	if (reduction != null && reduction.getId() != null) {
    		return new ResponseEntity<>(reduction, HttpStatus.CREATED);
    	} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}	
    }
    
    @ResponseBody
    @RequestMapping(value = "/{Id}/reductions/{reductionId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Contract> removeReduction(@PathVariable(name = "Id") final Integer contractId, @PathVariable(name = "reductionId") final Integer reductionId) {
    		
    	Contract contract = this.contractService.deleteReduction(contractId, reductionId);
    	return new ResponseEntity<>(contract, HttpStatus.OK);
		
    }
}
