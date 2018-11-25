package com.services.direct.data.output;

import org.springframework.format.annotation.NumberFormat;

import com.services.direct.utility.ProductUnit;

import lombok.Data;

@Data
public class BordereauDetailDto {

	private String uid;
	private String productUid;
	private Integer percentage;
	private String description;
	private String reference;
	private Integer qte;
	private ProductUnit unit;
	
	@NumberFormat(pattern = "######.###")
	private Double totalCommandLine;
	
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getProductUid() {
		return productUid;
	}
	public void setProductUid(String productUid) {
		this.productUid = productUid;
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
