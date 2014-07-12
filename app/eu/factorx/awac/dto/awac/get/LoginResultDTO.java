package eu.factorx.awac.dto.awac.get;

import eu.factorx.awac.dto.DTO;
import eu.factorx.awac.dto.myrmex.get.MyselfDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginResultDTO extends DTO {
    private MyselfDTO user;

    private Long defaultPeriod;

    private List<PeriodDTO> availablePeriods;
    private OrganizationDTO organization;

    public LoginResultDTO() {
    }

    public MyselfDTO getUser() {
        return user;
    }

    public void setUser(MyselfDTO user) {
        this.user = user;
    }

    public Long getDefaultPeriod() {
        return defaultPeriod;
    }

    public void setDefaultPeriod(Long defaultPeriod) {
        this.defaultPeriod = defaultPeriod;
    }

    public List<PeriodDTO> getAvailablePeriods() {
        return availablePeriods;
    }

    public void setAvailablePeriods(List<PeriodDTO> availablePeriods) {
        this.availablePeriods = availablePeriods;
    }

    public void setOrganization(OrganizationDTO organization) {
        this.organization = organization;
    }

    public OrganizationDTO getOrganization() {
        return organization;
    }
}
