package com.services.direct.bean;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import org.springframework.lang.Nullable;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@JsonIdentityInfo(
		  generator = ObjectIdGenerators.PropertyGenerator.class, 
		  property = "id")
public class Company {

	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="cmp_id")
	private Integer id;
	
	@NotNull
	@Column(name="cmp_name")
	private String name;
	
	@Column(name="cmp_address")
	private String address;
	
	@Column(name="cmp_city")
	private String city;
	
	@Column(name="cmp_phone_number")
	private String phoneNumber;
	
	@Column(name="cmp_fax_number")
	private String faxNumber;
	
	@NotNull
	@Column(name="cmp_tva_number")
	private String tvaNumber;

	@JsonIgnore
	@JsonInclude(Include.NON_NULL)
	@OneToOne(fetch=FetchType.EAGER, 
			cascade =  CascadeType.ALL,
			mappedBy="company")
	private Contact contact;
	
	@JsonIgnore
	@Nullable
	@OneToMany(fetch=FetchType.LAZY,
			cascade=CascadeType.ALL,
			mappedBy="company")
	// @JsonManagedReference
	private List<Invoice> invoices;
	
	@JsonIgnore
	@Nullable
	@OneToMany(fetch=FetchType.LAZY,
			cascade=CascadeType.ALL,
			mappedBy="company")
	// @JsonManagedReference
	private List<Bordereau> bordereaux;
	
	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "cmp_ctr_id", nullable = true)
	@JsonInclude(value=Include.NON_EMPTY, content=Include.NON_NULL)
	//@JsonBackReference
	private Contract contract;
	
	/**
	 * 
	 */
	public Company() {
	}



	/**
	 * @param id
	 * @param name
	 * @param address
	 * @param city
	 * @param phoneNumber
	 * @param faxNumber
	 * @param tvaNumber
	 * @param contact
	 * @param invoices
	 * @param bordereaux
	 */
	public Company(Integer id, @NotNull String name, String address, String city, String phoneNumber, String faxNumber,
			@NotNull String tvaNumber, Contact contact, List<Invoice> invoices, List<Bordereau> bordereaux) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.city = city;
		this.phoneNumber = phoneNumber;
		this.faxNumber = faxNumber;
		this.tvaNumber = tvaNumber;
		this.contact = contact;
		this.invoices = invoices;
		this.bordereaux = bordereaux;
	}

	
	
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getFaxNumber() {
		return faxNumber;
	}

	public void setFaxNumber(String faxNumber) {
		this.faxNumber = faxNumber;
	}

	public String getTvaNumber() {
		return tvaNumber;
	}

	public void setTvaNumber(String tvaNumber) {
		this.tvaNumber = tvaNumber;
	}

	public Contact getContact() {
		return contact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}

	public List<Invoice> getInvoices() {
		return invoices;
	}

	public void setInvoices(List<Invoice> invoices) {
		this.invoices = invoices;
	}
	
	public void addInvoices(Invoice invoice) {
		this.invoices.add(invoice);
	}

	public List<Bordereau> getBordereaux() {
		return bordereaux;
	}

	public void setBordereaux(List<Bordereau> bordereaux) {
		this.bordereaux = bordereaux;
	}
	
	public void addBordereau(Bordereau bordereau) {
		this.bordereaux.add(bordereau);
	}

	public Contract getContract() {
		return contract;
	}

	public void setContract(Contract contract) {
		this.contract = contract;
	}
	
	
}
