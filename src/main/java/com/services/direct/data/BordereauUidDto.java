package com.services.direct.data;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class BordereauUidDto {

	@NotNull(message = "Bordereau UID is required.")
	private String bordereauUid;

	public String getBordereauUid() {
		return bordereauUid;
	}

	public void setBordereauUid(String bordereauUid) {
		this.bordereauUid = bordereauUid;
	}

	
}
