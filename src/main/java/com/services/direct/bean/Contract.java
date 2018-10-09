package com.services.direct.bean;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Proxy;
import org.springframework.lang.Nullable;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.services.direct.utility.ContractType;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Proxy (lazy = false)
@JsonIdentityInfo(
		  generator = ObjectIdGenerators.PropertyGenerator.class, 
		  property = "id")
public class Contract {

	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ctr_id")
	private Integer id;
	
	@Column(name="ctr_name")
	private String name;
	
	@Nullable
	@OneToMany(fetch=FetchType.EAGER,
			// cascade={CascadeType.PERSIST, CascadeType.MERGE},
			mappedBy="contract")
	@JsonInclude(value=Include.NON_EMPTY, content=Include.NON_NULL)
	@JsonManagedReference
	private List<Reduction> reductions = new ArrayList<Reduction>();
	
	@Column(name="ctr_type")
	@Enumerated(EnumType.STRING)
	private ContractType type;
	
	@Nullable
	@OneToMany(fetch=FetchType.LAZY,
			mappedBy="contract")
	@JsonInclude(value=Include.NON_EMPTY, content=Include.NON_NULL)
	//@JsonManagedReference
	private List<Company> companies = new ArrayList<Company>();
	
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Reduction> getReductions() {
		return reductions;
	}
	public void setReductions(List<Reduction> reductions) {
		this.reductions = reductions;
	}
	
	public void addReduction(Reduction reduction) {
		this.reductions.add(reduction);
	}
	
	public ContractType getType() {
		return type;
	}
	public void setType(ContractType type) {
		this.type = type;
	}

	public List<Company> getCompanies() {
		return companies;
	}

	public void setCompanies(List<Company> companies) {
		this.companies = companies;
	}

	public void addCompany(Company company) {
		this.companies.add(company);
	}
}
