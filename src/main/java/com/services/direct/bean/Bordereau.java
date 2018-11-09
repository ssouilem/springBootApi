package com.services.direct.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Proxy;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.services.direct.utility.BrType;

@Entity
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@JsonIdentityInfo(
		  generator = ObjectIdGenerators.PropertyGenerator.class, 
		  property = "id")
public class Bordereau {

	@Id
	@Column(name="br_id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
    @Column(name = "br_uid", unique = true, length = 64)
    private String uid;
	
	@Column(name="br_number")
	private String number;
	
	@Column(name="br_type")
	@Enumerated(EnumType.STRING)
	private BrType type;
	
	@Temporal(TemporalType.DATE)
    @Column(name = "br_created_date")
	@JsonIgnore
    private Date createdDate = new Date();
	
	
	@Temporal(TemporalType.DATE)
    @Column(name = "br_treatment_date")
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	@JsonFormat(
		      shape = JsonFormat.Shape.STRING,
		      pattern = "YYYY-MM-dd")
    private Date treatmentDate;
	
	@Column(name = "br_created_author")
    private String createdAuthor;
	
	@ManyToOne
	@JoinColumn(name = "br_inv_id",  nullable = true)
	private Invoice invoice;
	
	@ManyToOne
	@JoinColumn(name = "br_cmp_id",  nullable = false)
	private Company company;
	
	@JsonInclude(value=Include.NON_EMPTY, content=Include.NON_NULL)
	@OneToMany(fetch=FetchType.EAGER,
			//cascade=CascadeType.ALL,
			mappedBy="bordereau")
	private List<BordereauDetail> bordereauDetails = new ArrayList<BordereauDetail>();
	
	@Column(name = "br_subtotal")
	private Double subTotal = 0.0;

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

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getTreatmentDate() {
		return treatmentDate;
	}

	public void setTreatmentDate(Date treatmentDate) {
		this.treatmentDate = treatmentDate;
	}

	public String getCreatedAuthor() {
		return createdAuthor;
	}

	public void setCreatedAuthor(String createdAuthor) {
		this.createdAuthor = createdAuthor;
	}

	public Invoice getInvoice() {
		return invoice;
	}

	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public List<BordereauDetail> getBordereauDetails() {
		return bordereauDetails;
	}

	public void setBordereauDetails(List<BordereauDetail> bordereauDetails) {
		this.bordereauDetails = bordereauDetails;
	}
	
	public void addBordereauDetail(BordereauDetail bordereauDetail) {
		this.bordereauDetails.add(bordereauDetail);
	}

	public Double getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(Double subTotal) {
		this.subTotal = subTotal;
	}
	
}
