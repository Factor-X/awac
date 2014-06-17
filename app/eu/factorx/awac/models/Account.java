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

import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
// import for JAXB annotations -- JAXB stack
import javax.xml.bind.annotation.XmlRootElement;

import play.data.validation.Constraints.Max;
import play.data.validation.Constraints.Min;
import play.data.validation.Constraints.Required;

import com.avaje.ebean.Page;

@Entity
@DiscriminatorValue("Account")

@XmlRootElement(name = "Account") // for JAXB
@XmlAccessorType(XmlAccessType.PROPERTY) // for JAXB
public class Account extends Person {
   
    //public int accessRights; // not used for now

	// specific fields for an account.
	@Required
	@Min(value = 18)
	@Max(value = 100)	
	public Integer age;	
	@Embedded
	public Vat vat;
	
    /*
     * Constructor
     */
    
    public Account(String identifier, String password, String lastname, String firstname)
    {
		super (identifier, password, lastname, firstname, new Address("","","",""));
    }
    
    /*
     * Finder
     */
	
    public static Finder<Long,Account> find = new Finder<Long,Account>(
        Long.class, Account.class
    );

   /**
     * Return a page of account
     *
     * @param page Page to display
     * @param pageSize Number of administrators per page
     * @param sortBy Administrator property used for sorting
     * @param order Sort order (either or asc or desc)
     * @param filter Filter applied on the name column
     */
    public static Page<Account> page(int page, int pageSize, String sortBy, String order, String filter) {
        return 
            find.where()
                .ilike("identifier", "%" + filter + "%")
                .orderBy(sortBy + " " + order)
                //.fetch("company")
                .findPagingList(pageSize)
                .getPage(page);
    }	

    
    /**
     * Return a list of account to select from
     * 
     **/
    
    public static Map<String,String> options() {
        LinkedHashMap<String,String> options = new LinkedHashMap<String,String>();
        for(Account a: Account.find.orderBy("identifier").findList()) {
            options.put(a.personId.toString(), a.identifier);
        }
        return options;
    }
}
