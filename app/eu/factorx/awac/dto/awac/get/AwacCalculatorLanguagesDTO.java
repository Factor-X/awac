package eu.factorx.awac.dto.awac.get;

import eu.factorx.awac.dto.DTO;

public class AwacCalculatorLanguagesDTO extends DTO {

    private boolean frEnabled;
    private boolean nlEnabled;
    private boolean enEnabled;

    public AwacCalculatorLanguagesDTO() {
    }

    public AwacCalculatorLanguagesDTO(boolean frEnabled, boolean nlEnabled, boolean enEnabled) {

        this.frEnabled = frEnabled;
        this.nlEnabled = nlEnabled;
        this.enEnabled = enEnabled;
    }

    public boolean isFrEnabled() {
        return frEnabled;
    }

    public boolean isNlEnabled() {
        return nlEnabled;
    }

    public boolean isEnEnabled() {
        return enEnabled;
    }
}
