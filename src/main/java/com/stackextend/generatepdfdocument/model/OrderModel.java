package com.stackextend.generatepdfdocument.model;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.NumberFormat;

import com.services.direct.bean.Company;
import com.services.direct.bean.Customer;
import com.services.direct.data.BordereauDetailPdf;

public class OrderModel {

    private String code;
    private Customer customer;
    private Company company;
    private Double amount;
    @NumberFormat(pattern = "#.###")
    private Double amountTVA;
    private Double otherExpenses;
    private Date createdDate;
    private Date issueDate;
    private String amountInLetter;
    private Boolean diplayTotalInLetter;
    private String remarks;
    
//    DecimalFormatSymbols symbols = new DecimalFormatSymbols();
//    symbols.setDecimalSeparator('.');
    private DecimalFormat df = new DecimalFormat("########.000"); 
    private SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
	
    private List<BordereauDetailPdf> entries;

    public OrderModel(String code, Customer customer, Company company, List<BordereauDetailPdf> bordereauDetails) {
        this.code = code;
        this.customer = customer;
        this.company = company;
        this.entries = bordereauDetails;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer address) {
        this.customer = address;
    }

    public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public List<BordereauDetailPdf> getEntries() {
        return entries;
    }

    public void setEntries(List<BordereauDetailPdf> entries) {
        this.entries = entries;
    }

	public Double getAmount() {
		return amount;
	}
	
	public void setAmount(Double amount) {
		this.amount = amount;
		this.amountTVA = amount * 0.200;
	}

	public String getAmountTVAString() {
		return df.format(amountTVA);
		
	}
	public Double getAmountTVA() {
		return amountTVA;
		
	}
	
	public String getAmountTTC() throws Exception {
		
		 DecimalFormatSymbols symbols = new DecimalFormatSymbols();
		 symbols.setDecimalSeparator('.');
		 DecimalFormat df = new DecimalFormat("0.000", symbols); 
		return df.format(amount + amountTVA + otherExpenses );
	}
	
	public void setAmountTVA(Double amount) {
		this.amountTVA = amount;
	}
	
	public Double getOtherExpenses() {
		return otherExpenses;
	}
	
	public String getOtherExpensesString() {
		DecimalFormatSymbols symbols = new DecimalFormatSymbols();
		symbols.setDecimalSeparator('.');
		DecimalFormat df = new DecimalFormat("0.000", symbols); 
		return df.format(otherExpenses);
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public String getCreatedDateString() {
		return sdf.format(createdDate);
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
	public Date getIssueDate() {
		return issueDate;
	}
	
	public String getIssueDateString() {
		return sdf.format(issueDate);
	}
	
	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}

	public void setOtherExpenses(Double otherExpenses) {
		this.otherExpenses = otherExpenses;
	}

	public String getAmountInLetter() {
		return amountInLetter;
	}

	public void setAmountInLetter(String amountInLetter) {
		this.amountInLetter = amountInLetter;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Boolean getDiplayTotalInLetter() {
		return diplayTotalInLetter;
	}

	public void setDiplayTotalInLetter(Boolean diplayTotalInLetter) {
		this.diplayTotalInLetter = diplayTotalInLetter;
	}
	
}
