package eu.factorx.awac.dto.admin.get;

import eu.factorx.awac.dto.DTO;
import eu.factorx.awac.dto.awac.get.PeriodDTO;

import java.util.List;

public class FactorsDTO extends DTO {

    private List<FactorDTO> factors;
    private List<PeriodDTO> periods;

    public FactorsDTO(List<FactorDTO> factors, List<PeriodDTO> periods) {
        this.factors = factors;
        this.periods = periods;
    }

    public List<FactorDTO> getFactors() {
        return factors;
    }

    public List<PeriodDTO> getPeriods() {
        return periods;
    }
}
