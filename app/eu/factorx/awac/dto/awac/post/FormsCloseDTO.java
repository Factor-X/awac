package eu.factorx.awac.dto.awac.post;

import eu.factorx.awac.dto.DTO;
import eu.factorx.awac.dto.validation.annotations.NotNull;
import eu.factorx.awac.dto.validation.annotations.Pattern;

/**
 * Created by florian on 29/09/14.
 */
public class FormsCloseDTO extends DTO {

    @NotNull
    @Pattern(regexp = Pattern.PASSWORD, message = "Password between 5 and 20 letters")
    private String password;

    @NotNull
    private String periodKey;

    @NotNull
    private Long scopeId;

    @NotNull
    private Boolean close;

    public FormsCloseDTO() {
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPeriodKey() {
        return periodKey;
    }

    public void setPeriodKey(String periodKey) {
        this.periodKey = periodKey;
    }

    public Long getScopeId() {
        return scopeId;
    }

    public void setScopeId(Long scopeId) {
        this.scopeId = scopeId;
    }

    public Boolean getClose() {
        return close;
    }

    public void setClose(Boolean close) {
        this.close = close;
    }

    @Override
    public String toString() {
        return "FormsCloseDTO{" +
                "password='" + password + '\'' +
                ", periodKey='" + periodKey + '\'' +
                ", scopeId=" + scopeId +
                ", close=" + close +
                '}';
    }
}
