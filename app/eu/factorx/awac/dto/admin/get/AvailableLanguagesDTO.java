package eu.factorx.awac.dto.admin.get;

import eu.factorx.awac.dto.DTO;
import eu.factorx.awac.dto.validation.annotations.NotNull;

public class AvailableLanguagesDTO extends DTO {

    private boolean frEnabled = false;
    private boolean nlEnabled = false;
    private boolean enEnabled = false;

	public AvailableLanguagesDTO() {
	}

	public AvailableLanguagesDTO(boolean frEnabled, boolean nlEnabled, boolean enEnabled) {
        this.frEnabled = frEnabled;
        this.nlEnabled = nlEnabled;
        this.enEnabled = enEnabled;
    }

    public boolean isFrEnabled() {
        return frEnabled;
    }

    public void setFrEnabled(boolean frEnabled) {
        this.frEnabled = frEnabled;
    }

    public boolean isNlEnabled() {
        return nlEnabled;
    }

    public void setNlEnabled(boolean nlEnabled) {
        this.nlEnabled = nlEnabled;
    }

    public boolean isEnEnabled() {
        return enEnabled;
    }

    public void setEnEnabled(boolean enEnabled) {
        this.enEnabled = enEnabled;
    }

    @Override
    public String toString() {
        return "AvailableLanguagesDTO{" +
                "frEnabled=" + frEnabled +
                ", nlEnabled=" + nlEnabled +
                ", enEnabled=" + enEnabled +
                '}';
    }
}


