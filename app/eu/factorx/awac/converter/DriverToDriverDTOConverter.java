package eu.factorx.awac.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import eu.factorx.awac.dto.admin.get.DriverDTO;
import eu.factorx.awac.models.data.question.Driver;
import eu.factorx.awac.models.data.question.DriverValue;

@Component
public class DriverToDriverDTOConverter implements Converter<Driver, DriverDTO> {


    @Autowired
    private DriverValueDriverValueDTOConverter driverValueDriverValueDTOConverter;

    @Override
    public DriverDTO convert(Driver driver) {
        DriverDTO driverDTO = new DriverDTO();

        driverDTO.setId(driver.getId());

        driverDTO.setName(driver.getName());

        for (DriverValue driverValue : driver.getDriverValueList()) {

            driverDTO.addDriverValue(driverValueDriverValueDTOConverter.convert(driverValue));
        }

        return driverDTO;
    }
}
