package eu.factorx.awac.dto.awac.post;

import eu.factorx.awac.dto.DTO;

/**
 * Created by florian on 5/11/14.
 */
public class ComputeAverageDTO extends DTO {

    private String interfaceName;
    private String periodKey;
    private String naceCodeListKey;
    private String naceCodeKey;
	private boolean onlyVerifiedForm;

	public ComputeAverageDTO() {
    }

    public String getPeriodKey() {
        return periodKey;
    }

    public void setPeriodKey(String periodKey) {
        this.periodKey = periodKey;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getNaceCodeListKey() {
        return naceCodeListKey;
    }

    public void setNaceCodeListKey(String naceCodeListKey) {
        this.naceCodeListKey = naceCodeListKey;
    }

    public String getNaceCodeKey() {
        return naceCodeKey;
    }

    public void setNaceCodeKey(String naceCodeKey) {
        this.naceCodeKey = naceCodeKey;
    }

    @Override
    public String toString() {
        return "ComputeAverageDTO{" +
                "interfaceName='" + interfaceName + '\'' +
                ", periodKey='" + periodKey + '\'' +
                '}';
    }

	public boolean isOnlyVerifiedForm() {
		return onlyVerifiedForm;
	}

	public void setOnlyVerifiedForm(boolean onlyVerifiedForm) {
		this.onlyVerifiedForm = onlyVerifiedForm;
	}
}
