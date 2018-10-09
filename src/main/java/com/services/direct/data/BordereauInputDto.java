package com.services.direct.data;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.services.direct.utility.BrType;

import lombok.Data;

@Data
public class BordereauInputDto {

	private String number;
	
	private BrType type;
	
	private String createdAuthor;

	@NotNull(message = "campany ID is required.")
	private Integer company;

//	@JsonFormat(pattern = "YYYY-MM-dd")
//	private String treatmentDateStr;

	private List<BordereauDetailDto> bordereauDetailList;

	 @JsonFormat(pattern = "YYYY-MM-dd")
	 @DateTimeFormat(pattern = "YYYY-MM-dd")
	 private String treatmentDate;

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public BrType getType() {
		return type;
	}

	public void setType(BrType type) {
		this.type = type;
	}

	public String getTreatmentDate() {
		return treatmentDate;
	}
	
	public String  getTreatmentDateStr() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.format(treatmentDate);
	}
	
	public void setTreatmentDate(String treatmentDate) {
		this.treatmentDate = treatmentDate;
	}
	
	public String getCreatedAuthor() {
		return createdAuthor;
	}

	public void setCreatedAuthor(String createdAuthor) {
		this.createdAuthor = createdAuthor;
	}

	public Integer getCompany() {
		return company;
	}

	public void setCompany(Integer company) {
		this.company = company;
	}

	public List<BordereauDetailDto> getBordereauDetailList() {
		return bordereauDetailList;
	}

	public void setBordereauDetailList(List<BordereauDetailDto> bordereauDetailList) {
		this.bordereauDetailList = bordereauDetailList;
	}

}
