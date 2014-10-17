package eu.factorx.awac.dto.myrmex.post;

import eu.factorx.awac.dto.DTO;


public class MainVerifierAccountDTO extends DTO{

	private String identifier;

	private Boolean isMainVerifier;

	public MainVerifierAccountDTO() {
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}


    public Boolean getIsMainVerifier() {
        return isMainVerifier;
    }

    public void setIsMainVerifier(Boolean isMainVerifier) {
        this.isMainVerifier = isMainVerifier;
    }

    @Override
    public String toString() {
        return "MainVerifierAccountDTO{" +
                "identifier='" + identifier + '\'' +
                ", isMainVerifier=" + isMainVerifier +
                '}';
    }
}
