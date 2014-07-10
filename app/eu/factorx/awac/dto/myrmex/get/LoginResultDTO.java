package eu.factorx.awac.dto.myrmex.get;

import eu.factorx.awac.dto.DTO;

import java.util.HashMap;
import java.util.Map;

public class LoginResultDTO extends DTO {
    private MyselfDTO user;

    private Long defaultPeriod;

    private Map<String,Long> availablePeriods;

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

    public Map<String, Long> getAvailablePeriods() {
        return availablePeriods;
    }

    public void setAvailablePeriods(Map<String, Long> availablePeriods) {
        this.availablePeriods = availablePeriods;
    }
}
