package eu.factorx.awac.converter;

import eu.factorx.awac.dto.admin.get.DriverDTO;
import eu.factorx.awac.dto.admin.get.DriverValueDTO;
import eu.factorx.awac.models.data.question.Driver;
import eu.factorx.awac.models.data.question.DriverValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class DriverValueDriverValueDTOConverter implements Converter<DriverValue, DriverValueDTO> {


    @Autowired
    private PeriodToPeriodDTOConverter periodToPeriodDTOConverter;

    @Override
    public DriverValueDTO convert(DriverValue driverValue) {
        DriverValueDTO driverValueDTO = new DriverValueDTO();

        driverValueDTO.setDefaultValue(driverValue.getDefaultValue());
        driverValueDTO.setFromPeriod(periodToPeriodDTOConverter.convert(driverValue.getFromPeriod()));

        return driverValueDTO;
    }
}
