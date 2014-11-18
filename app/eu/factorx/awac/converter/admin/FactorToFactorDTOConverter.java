package eu.factorx.awac.converter.admin;

import eu.factorx.awac.dto.admin.get.FactorDTO;
import eu.factorx.awac.dto.admin.get.FactorValueDTO;
import eu.factorx.awac.models.knowledge.Factor;
import eu.factorx.awac.models.knowledge.FactorValue;
import org.springframework.core.convert.converter.Converter;

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
            fv.setId(factorValue.getId());
            fv.setValue(factorValue.getValue());
            if (factorValue.getDateIn() != null) {
                fv.setDateIn(String.valueOf(factorValue.getDateIn()));
            }
            if (factorValue.getDateOut() != null) {
                fv.setDateOut(String.valueOf(factorValue.getDateOut()));
            }
            factorValueDTOs.add(fv);
        }

        return dto;
    }
}
