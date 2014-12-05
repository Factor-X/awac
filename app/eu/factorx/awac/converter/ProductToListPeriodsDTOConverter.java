package eu.factorx.awac.converter;

import eu.factorx.awac.dto.awac.post.ListPeriodsDTO;
import eu.factorx.awac.models.business.Product;
import eu.factorx.awac.models.knowledge.Period;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * Created by florian on 19/09/14.
 */
@Component
public class ProductToListPeriodsDTOConverter implements Converter<Product, ListPeriodsDTO> {

    @Autowired
    private PeriodToPeriodDTOConverter periodToPeriodDTOConverter;


    @Override
    public ListPeriodsDTO convert(Product product) {
        ListPeriodsDTO listPeriodsDTO = new ListPeriodsDTO();

        List<Period> listPeriodAvailable =product.getListPeriodAvailable();

        Collections.sort(listPeriodAvailable);

        for (Period period : listPeriodAvailable) {
            listPeriodsDTO.addPeriod(periodToPeriodDTOConverter.convert(period));
        }
        return listPeriodsDTO;

    }
}
