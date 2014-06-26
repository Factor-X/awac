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
 
package eu.factorx.awac.models;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
// import for JAXB annotations -- JAXB stack
import javax.xml.bind.annotation.XmlRootElement;
// import for Json annotations -- jackson stack
//import org.codehaus.jackson.annotate.*;

@XmlRootElement(name = "Address") // for JAXB
@XmlAccessorType(XmlAccessType.PROPERTY) // for JAXB

@Embeddable
public class Address implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public String street;
    public String postalcode;
    public String city;
	public String country;

    public Address()
    {
    }

    public Address (String street, String postalcode, String city, String country) {
	this.street = street;
	this.postalcode=postalcode;
	this.city = city;
	this.country = country;
    }
	
}
