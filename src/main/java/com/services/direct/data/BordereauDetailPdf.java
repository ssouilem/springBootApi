package com.services.direct.data;

import java.math.BigDecimal;

import javax.persistence.Column;

import org.springframework.format.annotation.NumberFormat;

import lombok.Data;

@Data
public class BordereauDetailPdf {

	/**
	 * @param percentage
	 * @param description
	 * @param qte
	 * @param price
	 * @param totalCommandLine
	 */
	public BordereauDetailPdf(Integer percentage, String description, Integer qte, BigDecimal price,
			Double totalCommandLine) {
		this.percentage = percentage;
		this.description = description;
		this.qte = qte;
		this.price = price;
		this.totalCommandLine = totalCommandLine;
	}

	private String uid;

	private Integer percentage;

	private String description;

	private Integer qte;

	private BigDecimal price;

	@NumberFormat(pattern = "######.###")
	@Column(name = "brd_total")
	private Double totalCommandLine;

	

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
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

	public Integer getQte() {
		return this.qte;
	}

	public void setQte(Integer qte) {
		this.qte = qte;
	}

	public Double getTotalCommandLine() {
		return totalCommandLine;
	}

	public void setTotalCommandLine(Double totalCommandLine) {
		this.totalCommandLine = totalCommandLine;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	
}
