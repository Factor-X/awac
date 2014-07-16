/*
 *
 * Instant Play Framework
 * AWAC
 *                       
 *
 * Copyright (c) 2014 Factor-X.
 * Author Gaston Hollands
 *
 */
 
package eu.factorx.awac.models.account;
 

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
// import for JAXB annotations -- JAXB stack
import javax.xml.bind.annotation.XmlRootElement;
// import for Json annotations -- jackson stack
//import org.codehaus.jackson.annotate.*;

@XmlRootElement(name = "Vat") // for JAXB
@XmlAccessorType(XmlAccessType.PROPERTY) // for JAXB

@Embeddable
public class Vat implements Serializable {

	private static final long serialVersionUID = 1L;

	private String vatNumber;
	private Boolean viesVerified;
	private String viesName;
	private String viesAddress;
	private String viesRequestId;
	private String viesRequestDate;
 

    protected Vat()
    {
    }

    public Vat (String vatNumber, boolean verified) {
	this.vatNumber = vatNumber;
	this.viesVerified = verified;
    }

	public String getVatNumber() {
		return vatNumber;
	}

	public void setVatNumber(String vatNumber) {
		this.vatNumber = vatNumber;
	}

	public Boolean getViesVerified() {
		return viesVerified;
	}

	public void setViesVerified(Boolean viesVerified) {
		this.viesVerified = viesVerified;
	}

	public String getViesName() {
		return viesName;
	}

	public void setViesName(String viesName) {
		this.viesName = viesName;
	}

	public String getViesAddress() {
		return viesAddress;
	}

	public void setViesAddress(String viesAddress) {
		this.viesAddress = viesAddress;
	}

	public String getViesRequestId() {
		return viesRequestId;
	}

	public void setViesRequestId(String viesRequestId) {
		this.viesRequestId = viesRequestId;
	}

	public String getViesRequestDate() {
		return viesRequestDate;
	}

	public void setViesRequestDate(String viesRequestDate) {
		this.viesRequestDate = viesRequestDate;
	}
    
}
