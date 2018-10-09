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

import com.services.direct.bean.Product;
import com.services.direct.data.ProductInputDto;
import com.services.direct.exception.BusinessException;
import com.services.direct.exception.FileNotFoundException;
import com.services.direct.service.ProductService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/products")
@Api(value="onlinestore", description="API REST product")
public class ProductController {
	
	private ProductService productService;
	 
	@Autowired
	public ProductController(final ProductService productService){
	  this.productService = productService;
	}
	
	@ApiOperation(value = "Create new product",response = Iterable.class)
	@RequestMapping(value = "/", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Product> createProduct(@RequestBody ProductInputDto productDto) throws BusinessException {
		Product product = this.productService.createProduct(productDto);
		if (product != null) {
			return new ResponseEntity<>(product, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}	
	}
	
	@ApiOperation(value = "View a list of available product",response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
	@RequestMapping(value = "/", method = RequestMethod.GET)
	@ResponseBody
	public List<Product> getAllProducts() {
		return this.productService.getAllProducts();
	}

	@RequestMapping(value = "/{productId}", method = RequestMethod.GET)
    @ResponseBody
    public Product getProductById(@PathVariable final Integer productId) throws BusinessException {
        return this.productService.getProductById(productId);
    }
	
	@RequestMapping(value="/{productId}", method=RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.OK)
    public void deleteProduct(@PathVariable Integer productId) {
        productService.deleteProductById(productId);;
    }
	
    @ResponseBody
    @RequestMapping(value = "/{Id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Product> updateProduct(@PathVariable(name = "Id") final Integer productId, @RequestBody ProductInputDto productDto) throws BusinessException {
    		
    	Product product = this.productService.updateProduct(productId, productDto);
    	
    	if (product != null) {
			return new ResponseEntity<>(product, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		
    }
}
