
package com.smm.ctrm.domain.Physical;
// default package

// Generated 2016-4-20 21:35:22 by Hibernate Tools 3.4.0.CR1

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * InvoiceNotice generated by hbm2java
 */
@Entity
@Table(name = "InvoiceNotice", schema = "Physical")
public class InvoiceNotice implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1384423384034676400L;
	private InvoiceNoticeId id;
	private Invoice invoice;

	public InvoiceNotice() {
	}

	public InvoiceNotice(InvoiceNoticeId id, Invoice invoice) {
		this.id = id;
		this.invoice = invoice;
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "invoiceId", column = @Column(name = "InvoiceId", nullable = false, length = 36)),
			@AttributeOverride(name = "storageId", column = @Column(name = "StorageId", nullable = false, length = 36)) })
	public InvoiceNoticeId getId() {
		return this.id;
	}

	public void setId(InvoiceNoticeId id) {
		this.id = id;
	}

	// @JsonBackReference("InvoiceNotice_")
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "InvoiceId", nullable = false, insertable = false, updatable = false)
	public Invoice getInvoice() {
		return this.invoice;
	}

	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}

}