package eu.factorx.awac.service.impl;

import eu.factorx.awac.models.data.question.Driver;
import eu.factorx.awac.service.DriverService;
import org.springframework.stereotype.Repository;
import play.db.jpa.JPA;

import java.util.List;

@Repository
public class DriverServiceImpl implements DriverService {

    @Override
    public List<Driver> findAll() {
        return (List<Driver>) JPA.em().createQuery(String.format("select e from %s e", Driver.class.getName())).getResultList();
    }

    @Override
    public Driver findById(final Long id) {
        return JPA.em().find(Driver.class, id);
    }

}