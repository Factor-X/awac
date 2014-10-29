package eu.factorx.awac.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import eu.factorx.awac.dto.admin.get.DriverValueDTO;
import eu.factorx.awac.models.data.question.DriverValue;

@Component
public class DriverValueDriverValueDTOConverter implements Converter<DriverValue, DriverValueDTO> {


    @Autowired
    private PeriodToPeriodDTOConverter periodToPeriodDTOConverter;

    @Override
    public DriverValueDTO convert(DriverValue driverValue) {
        DriverValueDTO driverValueDTO = new DriverValueDTO();

        driverValueDTO.setDefaultValue(driverValue.getDefaultValue());
        driverValueDTO.setFromPeriodKey(driverValue.getFromPeriod().getPeriodCode().getKey());

        return driverValueDTO;
    }
}
