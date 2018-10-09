package com.services.direct.service;

import java.util.List;

import com.services.direct.bean.Product;
import com.services.direct.data.ProductInputDto;
import com.services.direct.exception.BusinessException;
import com.services.direct.exception.FileNotFoundException;

public interface ProductService {

	Product getProductById(Integer productId) throws FileNotFoundException;
	
	Product createProduct(ProductInputDto productDto) throws BusinessException;
	
	Product updateProduct(Integer productId, ProductInputDto productDto) throws BusinessException;
	
	List<Product> getAllProducts();
	
	void deleteProductById(Integer productId);
	
}
