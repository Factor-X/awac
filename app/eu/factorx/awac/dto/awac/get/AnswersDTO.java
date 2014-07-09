package eu.factorx.awac.dto.awac.get;

import java.util.List;

import eu.factorx.awac.dto.DTO;
import eu.factorx.awac.dto.awac.post.AnswersSaveDTO;

public class AnswersDTO extends DTO {

	private AnswersSaveDTO answersSaveDTO;

	private Object valueContent;

	private List<UnitCategoryDTO> unitCategories;

	public AnswersDTO() {
		super();
	}

	public AnswersDTO(AnswersSaveDTO answersSaveDTO, Object valueContent, List<UnitCategoryDTO> unitCategories) {
		super();
		this.answersSaveDTO = answersSaveDTO;
		this.valueContent = valueContent;
		this.unitCategories = unitCategories;
	}

	public AnswersSaveDTO getAnswersSaveDTO() {
		return answersSaveDTO;
	}

	public void setAnswersSaveDTO(AnswersSaveDTO answersSaveDTO) {
		this.answersSaveDTO = answersSaveDTO;
	}

	public Object getValueContent() {
		return valueContent;
	}

	public void setValueContent(Object valueContent) {
		this.valueContent = valueContent;
	}

	public List<UnitCategoryDTO> getUnitCategories() {
		return unitCategories;
	}

	public void setUnitCategories(List<UnitCategoryDTO> unitCategories) {
		this.unitCategories = unitCategories;
	}

}
