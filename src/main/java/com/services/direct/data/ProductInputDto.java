package com.services.direct.data;

import java.math.BigDecimal;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.NumberFormat;

import com.services.direct.utility.ProductCategory;
import com.services.direct.utility.ProductChange;
import com.services.direct.utility.ProductUnit;

import lombok.Data;

@Data
public class ProductInputDto {

	@NotNull(message = "Product reference is required.")
	@NotEmpty(message = "reference not empty")
	private String reference;
	
	private String name;
	
	@NotNull(message = "Product description is required.")
	@NotEmpty(message = "description not empty")
	private String description;
	
	@NotNull(message = "Product unit is required.")
	@NotEmpty(message = "unit not empty")
	private ProductUnit unit;
	
	private ProductCategory category;

	@NumberFormat(pattern = "#.###")
	@NotEmpty(message = "reference not empty")
	private BigDecimal price;
	
	private ProductChange change;
	
	@NotEmpty(message = "tva not empty")
	private Double tva;

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ProductUnit getUnit() {
		return unit;
	}

	public void setUnit(ProductUnit unit) {
		this.unit = unit;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public ProductChange getChange() {
		return change;
	}

	public void setChange(ProductChange change) {
		this.change = change;
	}

	public ProductCategory getCategory() {
		return category;
	}

	public void setCategory(ProductCategory quality) {
		this.category = quality;
	}

	public Double getTva() {
		return tva;
	}

	public void setTva(Double tva) {
		this.tva = tva;
	}

}
