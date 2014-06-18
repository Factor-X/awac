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
import javax.persistence.Version;

import play.db.ebean.*;
import eu.factorx.awac.common.AccountStatusType;

import com.avaje.ebean.*;

// import for TimeStamp
import java.sql.Timestamp;


// imports for validation and constraints annotations
import javax.validation.*;

import play.data.validation.Constraints.*;

// import for Json annotations -- jackson stack
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.annotation.*;

// import for JAXB annotations -- JAXB stack
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement(name = "Person") // for JAXB
@XmlAccessorType(XmlAccessType.PROPERTY) // for JAXB
@Entity
@Access(AccessType.FIELD)
@DiscriminatorColumn(name = "PERSON_TYPE")
//@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
// JOINED not supported by ebeans
@Inheritance(strategy = InheritanceType.JOINED)
// @DiscriminatorValue("Personne")	
public abstract class Person extends Model {

    @Version // in order to improve optimistic locking.
    public Timestamp lastUpdate;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long personId;
    @Required
    public String identifier;
    @JsonIgnore // ignore password field when render in JSON
    @XmlTransient //ignore password field when render in XML
    @Required
    public String password;
    @Required
    public String lastname;
    @Required
    public String firstname;
    @Email
    public String email;
    @Embedded
    public Address address;
    // set status to unactive by default.
    @Required
    public AccountStatusType accountStatus = AccountStatusType.UNACTIVE;


    public Person() {
    }

    public Person(String identifier, String password, String lastname, String firstname) {
        // Dans le constructeur de la classe Personne
        this.identifier = identifier;
        this.password = password;
        this.lastname = lastname;
        this.firstname = firstname;
    }

    // for YAML load
    public Person(String identifier, String password, String lastname, String firstname, Address address) {
        // Dans le constructeur de la classe Personne
        this.identifier = identifier;
        this.password = password;
        this.lastname = lastname;
        this.firstname = firstname;
        this.address = address;
    }

    public static Finder<Long, Person> find = new Finder<Long, Person>(
            Long.class, Person.class
    );

    /**
     * Retrieve a Person from identifier
     */
    public static Person findByIdentifier(String identifier) {
        return find.where().eq("identifier", identifier).findUnique();
    }

    /**
     * Rename a Person with newName
     */
    public static String rename(Long personId, String newName) {
        play.Logger.info("entering Personne.rename - id: " + personId + " new name: " + newName);
        Person person = find.ref(personId);
        person.lastname = newName;
        person.update();
        return newName;
    }

    /**
     * getter and setter of embedded object address
     * useful to avoid NPE when saving an embedded object
     */

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

} // end of Person class
