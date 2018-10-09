package com.services.direct.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@JsonIdentityInfo(
		  generator = ObjectIdGenerators.PropertyGenerator.class, 
		  property = "id")
public class Reduction {
	
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	//@JsonProperty("id")
	@Column(name="red_id")
	private Integer id;
	
	// @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
	@ManyToOne
	@JoinColumn(name = "ct_pr_id", nullable = false)
	private Product product;
	
	@ManyToOne
	@JoinColumn(name = "red_ctr_id",  nullable = true)
	// @JsonProperty("contractId")
	//@JsonIgnore
	// @JsonBackReference
	private Contract contract;
	
	@Column(name="red_description")
	private String description;
	
	@Column(name="red_percentage")
	private Integer percentage;
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public Contract getContract() {
		return contract;
	}
	public void setContract(Contract contract) {
		this.contract = contract;
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
