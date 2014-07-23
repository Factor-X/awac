package eu.factorx.awac.dto.awac.get;

import java.util.ArrayList;
import java.util.List;

public class UnitCategoryDTO {

	private Long id;

	private Long mainUnitId;

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

	public UnitCategoryDTO(Long id, Long mainUnitId, List<UnitDTO> units) {
		super();
		this.id = id;
		this.mainUnitId = mainUnitId;
		this.units = units;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMainUnitId() {
		return mainUnitId;
	}

	public void setMainUnitId(Long mainUnitId) {
		this.mainUnitId = mainUnitId;
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
		return "UnitCategoryDTO [id=" + id + ", mainUnitId=" + mainUnitId + ", units=" + units + "]";
	}

}
