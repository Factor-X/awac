package eu.factorx.awac.dto.awac.get;

import eu.factorx.awac.dto.DTO;

public class UnitDTO extends DTO {

	private String code;

	private String name;

	protected UnitDTO() {
		super();
	}


    public UnitDTO(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

    @Override
    public String toString() {
        return "UnitDTO{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
