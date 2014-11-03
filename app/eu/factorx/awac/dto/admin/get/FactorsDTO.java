package eu.factorx.awac.dto.admin.get;

import eu.factorx.awac.dto.DTO;

import java.util.List;

public class FactorsDTO extends DTO {

    private List<FactorDTO> factors;

    public FactorsDTO(List<FactorDTO> factors) {
        this.factors = factors;
    }

    public List<FactorDTO> getFactors() {
        return factors;
    }
}
