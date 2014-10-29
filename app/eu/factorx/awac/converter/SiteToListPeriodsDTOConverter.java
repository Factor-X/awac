package eu.factorx.awac.converter;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import eu.factorx.awac.dto.awac.post.ListPeriodsDTO;
import eu.factorx.awac.models.business.Site;
import eu.factorx.awac.models.knowledge.Period;

/**
 * Created by florian on 19/09/14.
 */
@Component
public class SiteToListPeriodsDTOConverter implements Converter<Site, ListPeriodsDTO> {

    @Autowired
    private PeriodToPeriodDTOConverter periodToPeriodDTOConverter;


    @Override
    public ListPeriodsDTO convert(Site site) {
        ListPeriodsDTO listPeriodsDTO = new ListPeriodsDTO();

        List<Period> listPeriodAvailable = site.getListPeriodAvailable();

        Collections.sort(listPeriodAvailable);

        for (Period period : listPeriodAvailable) {
            listPeriodsDTO.addPeriod(periodToPeriodDTOConverter.convert(period));
        }
        return listPeriodsDTO;

    }
}
