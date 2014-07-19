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

import javax.persistence.Embeddable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

// import for JAXB annotations -- JAXB stack
// import for Json annotations -- jackson stack
//import org.codehaus.jackson.annotate.*;

@XmlRootElement(name = "Address")
// for JAXB
@XmlAccessorType(XmlAccessType.PROPERTY)
// for JAXB
@Embeddable
public class Address implements Serializable {

	private static final long serialVersionUID = 1L;

	private String street;
	private String postalcode;
	private String city;
	private String country;

	protected Address() {
	}

	public Address(String street, String postalcode, String city, String country) {
		this.street = street;
		this.postalcode = postalcode;
		this.city = city;
		this.country = country;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getPostalcode() {
		return postalcode;
	}

	public void setPostalcode(String postalcode) {
		this.postalcode = postalcode;
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

}
