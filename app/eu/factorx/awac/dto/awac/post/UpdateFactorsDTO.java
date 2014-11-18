package eu.factorx.awac.dto.awac.post;

import eu.factorx.awac.dto.DTO;
import eu.factorx.awac.dto.admin.get.FactorDTO;

import java.util.List;

public class UpdateFactorsDTO extends DTO {
    private List<FactorDTO> factors;

    public UpdateFactorsDTO(List<FactorDTO> factors) {
        this.factors = factors;
    }

    public void setFactors(List<FactorDTO> factors) {
        this.factors = factors;
    }

    public List<FactorDTO> getFactors() {
        return factors;
    }

}
