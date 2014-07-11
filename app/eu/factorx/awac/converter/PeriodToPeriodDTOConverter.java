package eu.factorx.awac.converter;

import eu.factorx.awac.dto.awac.get.PeriodDTO;
import eu.factorx.awac.dto.awac.get.SiteDTO;
import eu.factorx.awac.models.business.Site;
import eu.factorx.awac.models.knowledge.Period;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class PeriodToPeriodDTOConverter implements Converter<Period, PeriodDTO> {

    @Override
    public PeriodDTO convert(Period period) {
        PeriodDTO dto = new PeriodDTO();
        dto.setId(period.getId());
        dto.setLabel(period.getLabel());
        return dto;
    }
}
