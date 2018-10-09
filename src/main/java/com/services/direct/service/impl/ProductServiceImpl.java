package com.services.direct.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.services.direct.bean.Product;
import com.services.direct.data.ProductInputDto;
import com.services.direct.exception.BusinessException;
import com.services.direct.exception.FileNotFoundException;
import com.services.direct.mapping.EntityDTOMapper;
import com.services.direct.repo.ProductRepository;
import com.services.direct.service.ProductService;
import com.services.direct.utility.ProductUnit;

@Service("productService")
@Transactional
public class ProductServiceImpl implements ProductService {

	private static Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);
	
	@Autowired
	private EntityDTOMapper entityDTOMapper;

	private ProductRepository productRepository;

	@Autowired
	public ProductServiceImpl(final ProductRepository productRepository, EntityDTOMapper entityDTOMapper) {
		this.productRepository = productRepository;
		this.entityDTOMapper     = entityDTOMapper;
	}

	@Override
	@Transactional
	public Product getProductById(Integer productId) throws FileNotFoundException {
		log.info(" getProductById : " + productId);
		Product product = productRepository.getOne(productId);
		log.info(" produit -> reference : " + product.getReference());
		if (product != null) {
			return product;
		} else {
			throw new FileNotFoundException("The resource product was not found", "FILE_NOT_FOUND");
		}
	}

	@Override
	public Product createProduct(ProductInputDto productDto) throws BusinessException {
		
		log.info("creation de l'entite Produit");
		log.info("--> Quality : " + productDto.getQuality());
		Product product = entityDTOMapper.productDtotoProduct(productDto);
		
		if (product !=null && product.getPrice() != null && !product.getReference().isEmpty())
		{
			return productRepository.save(product);
		} else {
		   throw new FileNotFoundException("The resource reductions was not found", "FILE_NOT_FOUND");
		}
	}

	@Override
	public Product updateProduct(Integer productId, ProductInputDto productDto) throws BusinessException {
		
		Product product = this.productRepository.getOne(productId);
		Product productInput = entityDTOMapper.productDtotoProduct(productDto);

		if (product != null && product.equals(productInput)) {
			log.info("update produit equals entity DB");
			return product;
		} else if (productInput !=null) {
			log.info("update produitDto equals entity DB" + productInput.getQuality());
			
			product.setName(productInput.getName());
			product.setDescription(productInput.getDescription());
			product.setReference(productInput.getReference());
			product.setPrice(productInput.getPrice());
			product.setChange(productInput.getChange());
			product.setUnit(productInput.getUnit());
			product.setQuality(productInput.getQuality());
			
			return this.productRepository.save(product);
		} else {
			throw new FileNotFoundException("The resource product was not found", "FILE_NOT_FOUND");
		}
	}

	@Override
	public List<Product> getAllProducts() {
		List<Product> products = (List<Product>) productRepository.findAll();
		return products;
	}

	@Override
	public void deleteProductById(Integer productId) {
		this.productRepository.deleteById(productId);
		
	}
	
	
	
	
}
