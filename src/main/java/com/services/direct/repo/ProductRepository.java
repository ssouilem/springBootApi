package com.services.direct.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.services.direct.bean.Product;

@Repository
@Transactional
public interface ProductRepository extends JpaRepository<Product, Integer>{
	
	@Modifying
    @Query("UPDATE Product product SET product = :product WHERE product.id = :productId")
    int updateProduct(@Param("productId") int companyId, @Param("product") Product product);

	@Query("SELECT product FROM Product product WHERE product.id=(:id)")
	Product getProductById(@Param("id") Integer id);
	
	@Query("SELECT product FROM Product product WHERE product.uid=(:uid)")
	Product getProductByUID(@Param("uid") String productUid);

	@Modifying
	@Query("DELETE FROM Product product WHERE product.uid=(:uid)")
	void deleteProductByUID(@Param("uid") String productUid);
	
	@Query("SELECT product FROM Product product")
	List<Product> getAllProducts();
}