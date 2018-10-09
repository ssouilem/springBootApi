package com.services.direct.data;

import java.util.List;

import com.services.direct.utility.ContractType;

import lombok.Data;

@Data
public class ContractInputDto {

	private String name;
	private ContractType type;
	private List<ReductionDto> reductions;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ContractType getType() {
		return type;
	}

	public void setType(ContractType type) {
		this.type = type;
	}

	public List<ReductionDto> getReductions() {
		return reductions;
	}

	public void setReductions(List<ReductionDto> reductions) {
		this.reductions = reductions;
	}

}
