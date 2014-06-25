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

import com.fasterxml.jackson.annotation.JsonIgnore;
import eu.factorx.awac.common.AccountStatusType;
import play.data.validation.Constraints.Email;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlTransient;
import java.sql.Timestamp;

// import for TimeStamp
// imports for validation and constraints annotations
// import for Json annotations -- jackson stack
// import for JAXB annotations -- JAXB stack

@Entity
public class Person extends Model {

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

    @ManyToOne
    private Car car;

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
