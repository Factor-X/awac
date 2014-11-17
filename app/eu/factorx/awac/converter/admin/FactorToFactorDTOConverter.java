package eu.factorx.awac.converter.admin;

import eu.factorx.awac.dto.admin.get.FactorDTO;
import eu.factorx.awac.dto.admin.get.FactorValueDTO;
import eu.factorx.awac.models.knowledge.Factor;
import eu.factorx.awac.models.knowledge.FactorValue;
import org.springframework.core.convert.converter.Converter;

import java.util.Date;
import java.util.List;

public class FactorToFactorDTOConverter implements Converter<Factor, FactorDTO> {

    @Override
    public FactorDTO convert(Factor factor) {
        FactorDTO dto = new FactorDTO();

        // name
        dto.setKey(factor.getKey());

        // activity type
        dto.setIndicatorCategory(factor.getIndicatorCategory().getKey());
        dto.setActivitySource(factor.getActivitySource().getKey());
        dto.setActivityType(factor.getActivityType().getKey());

        // origin
        dto.setOrigin(factor.getInstitution());
        if (factor.getUnitIn().getCategory() != null) {
            dto.setUnitCategoryIn(factor.getUnitIn().getCategory().getName());
        }
        dto.setUnitIn(factor.getUnitIn().getSymbol());

        // values
        List<FactorValueDTO> factorValueDTOs = dto.getFactorValues();
        for (FactorValue factorValue : factor.getValues()) {
            FactorValueDTO fv = new FactorValueDTO();
            fv.setValue(factorValue.getValue());
            Date dateIn = factorValue.getDateIn();
            if (dateIn != null) {
                fv.setDateIn(dateIn.toString());
            }
            Date dateOut = factorValue.getDateOut();
            if (dateOut != null) {
                fv.setDateOut(dateOut.toString());
            }
            factorValueDTOs.add(fv);
        }

        return dto;
    }
}
