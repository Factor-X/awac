package eu.factorx.awac.service.impl;

import eu.factorx.awac.models.data.question.Driver;
import eu.factorx.awac.models.data.question.DriverValue;
import eu.factorx.awac.service.DriverService;
import org.springframework.stereotype.Component;
import play.db.jpa.JPA;

import java.util.List;

@Component
public class DriverServiceImpl extends AbstractJPAPersistenceServiceImpl<Driver> implements DriverService {
    @Override
    public Driver findByName(String name) {
        List<Driver> list = JPA.em().createQuery("select d from Driver d where d.name = :name", Driver.class).setParameter("name", name).getResultList();

        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    @Override
    public void remove(Driver entity) {
        for (DriverValue driverValue : entity.getDriverValueList()) {
            JPA.em().remove(driverValue);
        }
        super.remove(entity);
    }
}