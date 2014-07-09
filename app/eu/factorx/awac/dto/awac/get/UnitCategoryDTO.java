package eu.factorx.awac.dto.awac.get;

import java.util.ArrayList;
import java.util.List;

public class UnitCategoryDTO {

	private Long id;

	private String name;

	private List<UnitDTO> units;

	protected UnitCategoryDTO() {
		super();
	}

	public UnitCategoryDTO(Long id, String name, List<UnitDTO> units) {
		super();
		this.id = id;
		this.name = name;
		this.units = units;
	}

	public UnitCategoryDTO(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
		this.units = new ArrayList<>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
