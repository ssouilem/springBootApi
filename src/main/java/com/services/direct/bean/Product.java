package com.services.direct.bean;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.springframework.format.annotation.NumberFormat;
import org.springframework.lang.Nullable;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.services.direct.utility.ProductCategory;
import com.services.direct.utility.ProductChange;
import com.services.direct.utility.ProductUnit;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@JsonIdentityInfo(
		  generator = ObjectIdGenerators.PropertyGenerator.class, 
		  property = "id")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "pr_id")
	private Integer id;

    @Column(name = "pr_uid", unique = true, length = 64)
    private String uid;
    
	@Column(name = "pr_reference")
	private String reference;
	
	@Column(name = "pr_category")
	@Enumerated(EnumType.STRING)
	private ProductCategory category;

	@Column(name = "pr_name")
	private String name;

	@Column(name = "pr_description")
	private String description;

	@Column(name = "pr_unit")
	@Enumerated(EnumType.STRING)
	private ProductUnit unit;

	@NumberFormat(pattern = "######.###")
	@Column(name = "pr_price")
	private BigDecimal price;

	@Column(name = "pr_change")
	@Enumerated(EnumType.STRING)
	private ProductChange change;
	
	@Column(name = "pr_tva")
	private float tva;

	@Nullable
	@JsonInclude(Include.NON_NULL)
	@OneToMany(fetch = FetchType.EAGER, cascade =  CascadeType.REMOVE, mappedBy = "product")
	private List<Reduction> reductions;

	@JsonIgnore
	@JsonInclude(Include.NON_NULL)
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "product")
	private BordereauDetail bordereaudetail;
	
	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "pr_company_id", nullable = true)
	@JsonInclude(value=Include.NON_EMPTY, content=Include.NON_NULL)
	private Company company;
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public ProductCategory getCategory() {
		return category;
	}

	public void setCategory(ProductCategory quality) {
		this.category = quality;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ProductUnit getUnit() {
		return unit;
	}

	public void setUnit(ProductUnit unit) {
		this.unit = unit;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public ProductChange getChange() {
		return change;
	}

	public void setChange(ProductChange change) {
		this.change = change;
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

	public BordereauDetail getBordereaudetail() {
		return bordereaudetail;
	}

	public void setBordereaudetail(BordereauDetail bordereaudetail) {
		this.bordereaudetail = bordereaudetail;
	}
	
	public float getTva() {
		return tva;
	}

	public void setTva(float tva) {
		this.tva = tva;
	}

	
	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((change == null) ? 0 : change.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		result = prime * result + ((reference == null) ? 0 : reference.hashCode());
		result = prime * result + ((unit == null) ? 0 : unit.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		if (change != other.change)
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		if (reference == null) {
			if (other.reference != null)
				return false;
		} else if (!reference.equals(other.reference))
			return false;
		if (unit != other.unit)
			return false;
		return true;
	}

	
}
