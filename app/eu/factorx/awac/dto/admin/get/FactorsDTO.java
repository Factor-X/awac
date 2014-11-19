package eu.factorx.awac.dto.admin.get;

import eu.factorx.awac.dto.DTO;
import eu.factorx.awac.dto.awac.get.PeriodDTO;

import java.util.List;
import java.util.Set;

public class FactorsDTO extends DTO {

    private List<FactorDTO> factors;
    private List<PeriodDTO> periods;
    private List<String>    unitCategories;
    private Set<String>     indicatorCategories;
    private Set<String>     activityTypes;
    private Set<String>     activitySources;

    public FactorsDTO(List<FactorDTO> factors, List<PeriodDTO> periods, List<String> unitCategories, Set<String> indicatorCategories, Set<String> activityTypes, Set<String> activitySources) {
        this.factors = factors;
        this.periods = periods;
        this.unitCategories = unitCategories;
        this.indicatorCategories = indicatorCategories;
        this.activityTypes = activityTypes;
        this.activitySources = activitySources;
    }

    public List<FactorDTO> getFactors() {
        return factors;
    }

    public List<PeriodDTO> getPeriods() {
        return periods;
    }

    public List<String> getUnitCategories() {
        return unitCategories;
    }

    public Set<String> getActivityTypes() {
        return activityTypes;
    }

    public Set<String> getActivitySources() {
        return activitySources;
    }

    public Set<String> getIndicatorCategories() {
        return indicatorCategories;
    }
}
