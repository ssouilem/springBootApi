package com.services.direct.data;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.NumberFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.services.direct.utility.ProductUnit;

import lombok.Data;

@Data
public class BordereauDetailDto {

	@NotNull(message = "productId is required.")
	private Integer productId;
	
	private Integer percentage;
	private String description;
	private String reference;
	private Integer qte;
	private ProductUnit unit;
	
	@NumberFormat(pattern = "######.###")
	private Double totalCommandLine;
	
	
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public Integer getPercentage() {
		return percentage;
	}
	public void setPercentage(Integer percentage) {
		this.percentage = percentage;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getReference() {
		return reference;
	}
	public void setReference(String reference) {
		this.reference = reference;
	}
	public Integer getQte() {
		return qte;
	}
	public void setQte(Integer qte) {
		this.qte = qte;
	}
	public ProductUnit getUnit() {
		return unit;
	}
	public void setUnit(ProductUnit unit) {
		this.unit = unit;
	}
	
	public Double getTotalCommandLine() {
		return totalCommandLine;
	}
	public void setTotalCommandLine(Double totalCommandLine) {
		this.totalCommandLine = totalCommandLine;
	}
}
