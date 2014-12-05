package eu.factorx.awac.service;

import eu.factorx.awac.models.data.question.Driver;
import eu.factorx.awac.models.data.question.DriverValue;

public interface DriverService extends PersistenceService<Driver> {

    Driver findByName(String name);

}

