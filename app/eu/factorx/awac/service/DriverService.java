package eu.factorx.awac.service;

import java.util.List;

import eu.factorx.awac.models.data.question.Driver;

/**
 * Created by florian on 21/10/14.
 */
public interface DriverService {
    List<Driver> findAll();

    Driver findById(Long driverId);
}

