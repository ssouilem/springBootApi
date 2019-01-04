package com.services.direct.bean;

import java.util.List;

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
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@JsonIdentityInfo(
		  generator = ObjectIdGenerators.PropertyGenerator.class, 
		  property = "id")
public class Customer {

	private static final long serialVersionUID = -4572195412018767502L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="cus_id")
	private Integer id;
	
    @Column(name = "cus_uid", unique = true, length = 64)
    private String uid;
	
	@NotNull
	@Column(name="cus_name")
	private String name;
	
	@Column(name="cus_mail")
	private String mail;

	@Column(name="cus_address")
	private String address;
	
	@Column(name="cus_additionalAddress")
	private String additionalAddress;
	
	@Column(name="cus_zipe_code")
	private String postalCode;
	
	@Column(name="cus_city")
	private String city;
	
	@Column(name="cus_country")
	private String country;
	
	@Column(name="cus_phone_number")
	private String phoneNumber;
	
	@Column(name="cus_fax_number")
	private String faxNumber;
	
	@NotNull
	@Column(name="cus_tva_number")
	private String siret;

	@JsonProperty("contact")
	@JsonInclude(value=Include.NON_EMPTY, content=Include.NON_NULL)
	@OneToOne(orphanRemoval=true, fetch=FetchType.EAGER, 
			mappedBy="customer")
	private Contact contact;
	
	@JsonIgnore
	@Nullable
	@OneToMany(orphanRemoval=true, fetch=FetchType.LAZY,
//			cascade=CascadeType.ALL,
			mappedBy="customer")
	private List<Invoice> invoices;
	
	@JsonIgnore
	@Nullable
	@OneToMany(orphanRemoval=true, fetch=FetchType.LAZY,
			//cascade=CascadeType.ALL,
			mappedBy="customer")
	private List<Bordereau> bordereaux;
	
	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "cus_ctr_id", nullable = true)
	@JsonInclude(value=Include.NON_EMPTY, content=Include.NON_NULL)
	private Contract contract;
	
	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "cus_company_id", nullable = true)
	@JsonInclude(value=Include.NON_EMPTY, content=Include.NON_NULL)
	private Company company;
	
	public Integer getId() {
		return id;
	}
	
	public void setUid(String uid) {
		this.uid = uid;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUid() {
		return uid;
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

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getAdditionalAddress() {
		return additionalAddress;
	}

	public void setAdditionalAddress(String additionalAddress) {
		this.additionalAddress = additionalAddress;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
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

	public String getSiret() {
		return siret;
	}

	public void setSiret(String tvaNumber) {
		this.siret = tvaNumber;
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

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}
}
