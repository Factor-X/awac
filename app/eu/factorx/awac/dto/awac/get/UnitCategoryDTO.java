package eu.factorx.awac.dto.awac.get;

import java.util.ArrayList;
import java.util.List;

public class UnitCategoryDTO {

	private Long id;

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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

}
