package com.services.direct.data;

import java.math.BigDecimal;

import org.springframework.format.annotation.NumberFormat;

import com.services.direct.utility.ProductChange;
import com.services.direct.utility.ProductQuality;
import com.services.direct.utility.ProductUnit;

import lombok.Data;

@Data
public class ProductInputDto {

	private String reference;
	private String name;
	private String description;
	private ProductUnit unit;
	private ProductQuality quality;

	@NumberFormat(pattern = "#.###")
	private BigDecimal price;
	
	private ProductChange change;

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

	public ProductQuality getQuality() {
		return quality;
	}

	public void setQuality(ProductQuality quality) {
		this.quality = quality;
	}

}
