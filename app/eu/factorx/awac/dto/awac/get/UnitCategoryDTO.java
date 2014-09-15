package eu.factorx.awac.dto.awac.get;

import java.util.ArrayList;
import java.util.List;

public class UnitCategoryDTO {

	private Long id;

	private String mainUnitCode;

	private List<UnitDTO> units;

	protected UnitCategoryDTO() {
		super();
	}

	public UnitCategoryDTO(Long id, List<UnitDTO> units) {
		super();
		this.id = id;
		this.units = units;
	}

	public UnitCategoryDTO(Long id) {
		super();
		this.id = id;
		this.units = new ArrayList<>();
	}

	public UnitCategoryDTO(Long id, String mainUnitCode, List<UnitDTO> units) {
		super();
		this.id = id;
		this.mainUnitCode = mainUnitCode;
		this.units = units;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

    public String getMainUnitCode() {
        return mainUnitCode;
    }

    public void setMainUnitCode(String mainUnitCode) {
        this.mainUnitCode = mainUnitCode;
    }

    public List<UnitDTO> getUnits() {
		return units;
	}

	public void setUnits(List<UnitDTO> units) {
		this.units = units;
	}

	public boolean addUnit(UnitDTO unitDTO) {
		return getUnits().add(unitDTO);
	}

    @Override
    public String toString() {
        return "UnitCategoryDTO{" +
                "id=" + id +
                ", mainUnitCode='" + mainUnitCode + '\'' +
                ", units=" + units +
                '}';
    }
}
