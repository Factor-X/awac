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
 

import javax.persistence.*;

import play.db.ebean.*;

import com.avaje.ebean.*;

import javax.persistence.Embeddable;



// import for Json annotations -- jackson stack
//import org.codehaus.jackson.annotate.*;
import com.fasterxml.jackson.annotation.*;

// import for JAXB annotations -- JAXB stack
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@XmlRootElement(name = "Vat") // for JAXB
@XmlAccessorType(XmlAccessType.PROPERTY) // for JAXB

@Embeddable
public class Vat extends Model {

	@JsonIgnore // ignore id field when render in JSON
    @Id
    private Long id;
	public String vatNumber;
	public boolean viesVerified;
	public String viesName;
    public String viesAddress;
    public String viesRequestId;
    public String viesRequestDate;
 

    public Vat()
    {
    }

    public Vat (String vatNumber, boolean verified) {
	this.vatNumber = vatNumber;
	this.viesVerified = verified;
    }
	
	/* not useful for Play  but need for yamlll */
	
	public Long getId() {
	    return (id);
    }

    public void setId (Long id) {
	    this.id = id;
    }
}
