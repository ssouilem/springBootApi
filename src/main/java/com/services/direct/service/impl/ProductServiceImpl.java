package com.services.direct.service.impl;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.errors.ErrorDto;
import com.services.direct.bean.Product;
import com.services.direct.bean.security.User;
import com.services.direct.data.ProductInputDto;
import com.services.direct.exception.BusinessException;
import com.services.direct.exception.DuplicateEntityException;
import com.services.direct.exception.FileNotFoundException;
import com.services.direct.mapping.EntityDTOMapper;
import com.services.direct.repo.ProductRepository;
import com.services.direct.service.ProductService;

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
	public Product getProductByUID(String productUid) throws FileNotFoundException {
		log.info(" getProductById : " + productUid);
		Product product = productRepository.getProductByUID(productUid);
		if (product != null) {
			log.info(" produit -> reference : " + product.getReference());
			return product;
		} else {
			throw new FileNotFoundException("The resource product was not found", "FILE_NOT_FOUND");
		}
	}

	@Override
	@Transactional
	public Product createProduct(ProductInputDto productDto, User user) throws BusinessException {
		
		Product product = entityDTOMapper.productDtotoProduct(productDto);
		UUID uuid = UUID.randomUUID();
		product.setUid(uuid.toString());
		product.setCompany(user.getCompany());
		
		try {
			if (product.getDescription().isEmpty()) {
				throw new BusinessException("PRODUCT_ERROR")
					.add(new ErrorDto("DESCRIPTION_ERROR", "La description est vide"));
			} else if (product.getName().isEmpty()) {
				throw new BusinessException("PRODUCT_ERROR")
				.add(new ErrorDto("NAME_ERROR", "Le nom de produit est vide"));
			} else if (product.getPrice().doubleValue() == 0) {
				throw new BusinessException("PRODUCT_ERROR")
				.add(new ErrorDto("PRICE_ERROR", "Le prix de produit est vide"));
			} else if (product.getReference().isEmpty()) {
				throw new BusinessException("PRODUCT_ERROR")
				.add(new ErrorDto("REFERENCE_ERROR", "La reference de produit est vide"));
			} else {
				if (product !=null && product.getPrice() != null && !product.getReference().isEmpty())
				{
					// verification de doublon
					if (productRepository.findByReferenceOrName(product.getReference(), product.getName()) == 0) {
							return productRepository.save(product);
					} else {
						throw new DuplicateEntityException("DUPLICATE_PRODUCT")
							.add(new ErrorDto("DUPLICATE_PRODUCT", "The product already exists in base"));
					}
				} else {
				   throw new FileNotFoundException("PRODUCT_NOT_FOUND");
				}
			}
		} catch (NullPointerException ex) {
			throw new FileNotFoundException("INTERNAL_ERROR")
			.add(new ErrorDto("REQUEST_ERROR", ex.getMessage()));
		}
	}

	@Override
	@Transactional
	public Product updateProduct(String productUid, ProductInputDto productDto) throws BusinessException {
		
		if (productRepository.isExistProductByUID(productUid) > 0) {
			
			Product product = this.productRepository.getProductByUID(productUid);
			Product productInput = entityDTOMapper.productDtotoProduct(productDto);
	
			if (product != null && product.equals(productInput)) {
				log.info("update produit equals entity DB");
				return product;
			} else {
				log.info("update produitDto equals entity DB" + productInput.getCategory());
				
				product.setName(productInput.getName());
				product.setDescription(productInput.getDescription());
				product.setReference(productInput.getReference());
				product.setPrice(productInput.getPrice());
				product.setChange(productInput.getChange());
				product.setUnit(productInput.getUnit());
				product.setCategory(productInput.getCategory());
				
				return this.productRepository.save(product);
			}
		} else
			throw new FileNotFoundException("PRODUCT_NOT_EXIST");	
	}

	@Override
	@Transactional
	public List<Product> getAllProducts(Integer companyId) {
		List<Product> products = productRepository.getAllProductsByCompany(companyId);
		return products;
	}

	@Override
	@Transactional
	public void deleteProductByUID(String productUid) throws BusinessException {
		
		try {
			if (productRepository.isExistProductByUID(productUid) > 0)
				this.productRepository.deleteProductByUID(productUid);
			else
				throw new BusinessException("NOT_EXIST");
			
		} catch (Exception e) {
			throw new FileNotFoundException("PRODUCT_USED");
		} 	
	}
	
	
}
