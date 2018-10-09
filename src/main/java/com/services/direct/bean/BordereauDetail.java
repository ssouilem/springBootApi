package com.services.direct.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.springframework.format.annotation.NumberFormat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class BordereauDetail {

	@Id
	@Column(name = "brd_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@JsonInclude(value=Include.NON_EMPTY, content=Include.NON_NULL)
	@OneToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "brd_pr_id", nullable = false)
	private Product product;

	@Column(name = "pr_percentage")
	private Integer percentage;

	@Column(name = "pr_description")
	private String description;

	@Column(name = "pr_qte")
	private Integer qte;

	@ManyToOne
	@JoinColumn(name = "brd_br_id", nullable = true)
	private Bordereau bordereau;

	@NumberFormat(pattern = "######.###")
	@Column(name = "pr_total")
	private Double totalCommandLine;

	// /**
	// * @param produit
	// * @param percentage
	// * @param qte
	// */
	// public BordereauDetail(Product product, Integer percentage, Integer qte) {
	// super();
	// this.product = product;
	// this.percentage = percentage;
	// this.qte = qte;
	// }

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

	public Integer getPercentage() {
		return percentage;
	}

	public void setPercentage(Integer percentage) {
		this.percentage = percentage;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getQte() {
		return this.qte;
	}

	public void setQte(Integer qte) {
		this.qte = qte;
	}

	public Bordereau getBordereau() {
		return bordereau;
	}

	public void setBordereau(Bordereau bordereau) {
		this.bordereau = bordereau;
	}

	public Double getTotalCommandLine() {
		return totalCommandLine;
	}

	public void setTotalCommandLine(Double totalCommandLine) {
		this.totalCommandLine = totalCommandLine;
	}

}
