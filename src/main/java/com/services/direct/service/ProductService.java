package com.services.direct.service;

import java.util.List;

import com.services.direct.bean.Product;
import com.services.direct.data.ProductInputDto;
import com.services.direct.exception.BusinessException;
import com.services.direct.exception.FileNotFoundException;

public interface ProductService {

	Product createProduct(ProductInputDto productDto) throws BusinessException;
	
	List<Product> getAllProducts();

	Product getProductByUID(String productUid) throws FileNotFoundException;

	Product updateProduct(String productUid, ProductInputDto productDto) throws BusinessException;

	void deleteProductByUID(String productUid);
	
}
