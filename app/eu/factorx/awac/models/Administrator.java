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

import com.avaje.ebean.Page;

import javax.persistence.Entity;

// import for JAXB annotations -- JAXB stack

@Entity
public class Administrator extends Person {

    //public int accessRights; // not used for now


    public Administrator(String identifier, String password, String lastname, String firstname) {
        super(identifier, password, lastname, firstname, new Address("", "", "", ""));
    }

    public static Finder<Long, Administrator> find = new Finder<Long, Administrator>(
            Long.class, Administrator.class
    );

    /**
     * Return a page of administrator
     *
     * @param page     Page to display
     * @param pageSize Number of administrators per page
     * @param sortBy   Administrator property used for sorting
     * @param order    Sort order (either or asc or desc)
     * @param filter   Filter applied on the name column
     */
    public static Page<Administrator> page(int page, int pageSize, String sortBy, String order, String filter) {
        return
                find.where()
                        .ilike("identifier", "%" + filter + "%")
                        .orderBy(sortBy + " " + order)
                                //.fetch("company")
                        .findPagingList(pageSize)
                        .getPage(page);
    }
}
