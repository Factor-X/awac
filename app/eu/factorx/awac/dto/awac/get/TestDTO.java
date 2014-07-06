package eu.factorx.awac.dto.awac.get;

import java.util.List;

import eu.factorx.awac.dto.DTO;

public class TestDTO extends DTO {

	private List<? extends Object> resultList;

	public TestDTO(List<? extends Object> resultList) {
		super();
		this.resultList = resultList;
	}

	public TestDTO() {
		super();
	}

	public List<? extends Object> getResultList() {
		return resultList;
	}

	public void setResultList(List<? extends Object> resultList) {
		this.resultList = resultList;
	}
	
}
