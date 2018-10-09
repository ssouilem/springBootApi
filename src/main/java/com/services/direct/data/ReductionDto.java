package com.services.direct.data;

import lombok.Data;

@Data
public class ReductionDto {

	private Integer productId;
	private String description;
	private Integer percentage;
	
	// private Integer contractId;

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getPercentage() {
		return percentage;
	}

	public void setPercentage(Integer percentage) {
		this.percentage = percentage;
	}



	
}
