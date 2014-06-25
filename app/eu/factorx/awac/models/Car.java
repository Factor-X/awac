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

import play.db.ebean.Model;

import javax.persistence.*;

// import for JAXB annotations -- JAXB stack

@Entity
public class Car extends Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany
    private Person person;

    public Car() {

    }

    public static Finder<Long, Car> find = new Finder<Long, Car>(
            Long.class, Car.class
    );


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
