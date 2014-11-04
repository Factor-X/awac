package eu.factorx.awac.converter.admin;

import eu.factorx.awac.dto.admin.get.FactorDTO;
import eu.factorx.awac.models.knowledge.Factor;
import org.springframework.core.convert.converter.Converter;

public class FactorToFactorDTOConverter implements Converter<Factor, FactorDTO> {

    @Override
    public FactorDTO convert(Factor factor) {
        FactorDTO dto = new FactorDTO();

        // name
        dto.setKey(factor.getKey());

        // description
        dto.setDescription("");

        // activity type
        dto.setType(factor.getActivityType().getKey());

        // origin
        dto.setOrigin(factor.getInstitution());




        return dto;
    }
}
