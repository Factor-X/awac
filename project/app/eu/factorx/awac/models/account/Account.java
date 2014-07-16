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

import javax.persistence.DiscriminatorValue;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import play.data.validation.Constraints.Max;
import play.data.validation.Constraints.Min;
import play.data.validation.Constraints.Required;
import eu.factorx.awac.models.business.Organization;

// import for JAXB annotations -- JAXB stack

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue("account")
@NamedQueries({
	   @NamedQuery(name = Account.FIND_BY_IDENTIFIER, query = "select p from Account p where p.identifier = :identifier"),
	})
public class Account extends Person {

	private static final long serialVersionUID = 1L;

	/**
	 * :identifier = ...
	 */
    public static final String FIND_BY_IDENTIFIER = "Account.findByIdentifier";

    //public int accessRights; // not used for now

	// specific fields for an account.

	@Required
    @Min(value = 18)
    @Max(value = 100)
    private Integer age;

	@Embedded
    private Vat vat;

	@ManyToOne(optional = false)
	private Organization organization;

	protected Account() {
		super();
	}

    /*
     * Constructor
     */

    public Account(Organization organization, String identifier, String password, String lastname, String firstname) {
        super(identifier, password, lastname, firstname, new Address("", "", "", ""));
        this.organization = organization;
    }

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Vat getVat() {
		return vat;
	}

	public void setVat(Vat vat) {
		this.vat = vat;
	}

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

    public String toString() {
        String string = super.toString();
        string=string.concat("age:"+age);
        return string;
    }

    /*
     * Finder
     */

//    public static Finder<Long, Account> find = new Finder<Long, Account>(
//            Long.class, Account.class
//    );

    /**
     * Return a page of account
     *
     * @param page     Page to display
     * @param pageSize Number of administrators per page
     * @param sortBy   Administrator property used for sorting
     * @param order    Sort order (either or asc or desc)
     * @param filter   Filter applied on the name column
     */
//    public static Page<Account> page(int page, int pageSize, String sortBy, String order, String filter) {
//        return
//                find.where()
//                        .ilike("identifier", "%" + filter + "%")
//                        .orderBy(sortBy + " " + order)
//                                //.fetch("company")
//                        .findPagingList(pageSize)
//                        .getPage(page);
//    }


    /**
     * Return a list of account to select from
     */

//    public static Map<String, String> options() {
//        LinkedHashMap<String, String> options = new LinkedHashMap<String, String>();
//        for (Account a : Account.find.orderBy("identifier").findList()) {
//            options.put(a.personId.toString(), a.identifier);
//        }
//        return options;
//    }
}
