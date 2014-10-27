package eu.factorx.awac.service;

import eu.factorx.awac.models.data.question.Driver;

import java.util.List;

/**
 * Created by florian on 21/10/14.
 */
public interface DriverService {
    List<Driver> findAll();

    Driver findById(Long driverId);
}

