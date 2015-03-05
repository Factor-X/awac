package eu.factorx.awac.dto.admin.get;

import eu.factorx.awac.dto.DTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by florian on 21/10/14.
 */
public class DriverDTO extends DTO implements Comparable<DriverDTO> {

	private Long id;

	private String name;

	private List<DriverValueDTO> driverValues;

	private Set<String> calculatorNames;

	private String expectedValueType;

	public DriverDTO() {
	}

	public DriverDTO(String name, List<DriverValueDTO> driverValues) {
		this.name = name;
		this.driverValues = driverValues;
	}

	public String getExpectedValueType() {
		return expectedValueType;
	}

	public void setExpectedValueType(String expectedValueType) {
		this.expectedValueType = expectedValueType;
	}

	public Set<String> getCalculatorNames() {
		return calculatorNames;
	}

	public void setCalculatorNames(Set<String> calculatorNames) {
		this.calculatorNames = calculatorNames;
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

	public List<DriverValueDTO> getDriverValues() {
		return driverValues;
	}

	public void setDriverValues(List<DriverValueDTO> driverValues) {
		this.driverValues = driverValues;
	}

	public void addDriverValue(DriverValueDTO driverValueDTO) {
		if (this.driverValues == null) {
			this.driverValues = new ArrayList<>();
		}

		this.driverValues.add(driverValueDTO);
	}

	@Override
	public String toString() {
		return "DriverDTO{" +
			"driverValues=" + driverValues +
			", name='" + name + '\'' +
			'}';
	}

	@Override
	public int compareTo(DriverDTO o) {
		if (this.getCalculatorNames().size() == 0) {
			return -1;

		} else if (o.getCalculatorNames().size() == 0) {
			return 1;
		}
		return this.getCalculatorNames().iterator().next().compareTo(o.getCalculatorNames().iterator().next());
	}
}
