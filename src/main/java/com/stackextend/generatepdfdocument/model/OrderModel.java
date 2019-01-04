package com.stackextend.generatepdfdocument.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.services.direct.bean.BordereauDetail;
import com.services.direct.bean.Company;
import com.services.direct.bean.Customer;

public class OrderModel {

    private String code;
    private Customer customer;
    private Company company;
    private Double amount;
    private Date createdDate;
    private Date issueDate;
    
    private SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
	
    private List<BordereauDetail> entries;

    public OrderModel(String code, Customer customer, Company company, List<BordereauDetail> entries) {
        this.code = code;
        this.customer = customer;
        this.company = company;
        this.entries = entries;
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

	public List<BordereauDetail> getEntries() {
        return entries;
    }

    public void setEntries(List<BordereauDetail> entries) {
        this.entries = entries;
    }

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
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
    
}
