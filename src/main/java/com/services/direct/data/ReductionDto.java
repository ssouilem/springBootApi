package com.services.direct.data;

import lombok.Data;

@Data
public class ReductionDto {

	private String productUid;
	private String description;
	private Integer percentage;
	
	// private Integer contractId;

	public String getProductUid() {
		return productUid;
	}

	public void setProductUid(String productUid) {
		this.productUid = productUid;
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
