package eu.factorx.awac.dto.awac.get;

import java.util.List;

import eu.factorx.awac.dto.DTO;
import eu.factorx.awac.dto.awac.post.AnswersSaveDTO;

public class FormDTO extends DTO {

	private List<QuestionSetDTO> questionSets;

	private List<UnitCategoryDTO> unitCategories;

	private List<CodeListDTO> codeLists;

	private AnswersSaveDTO answersSaveDTO;

	public FormDTO() {
		super();
	}

	/**
	 * @param questionSets
	 * @param unitCategories
	 * @param codeLists
	 * @param answersSaveDTO
	 */
	public FormDTO(List<QuestionSetDTO> questionSets, List<UnitCategoryDTO> unitCategories,
			List<CodeListDTO> codeLists, AnswersSaveDTO answersSaveDTO) {
		super();
		this.questionSets = questionSets;
		this.unitCategories = unitCategories;
		this.codeLists = codeLists;
		this.answersSaveDTO = answersSaveDTO;
	}

	public List<QuestionSetDTO> getQuestionSets() {
		return questionSets;
	}

	public void setQuestionSets(List<QuestionSetDTO> questionSets) {
		this.questionSets = questionSets;
	}

	public List<UnitCategoryDTO> getUnitCategories() {
		return unitCategories;
	}

	public void setUnitCategories(List<UnitCategoryDTO> unitCategories) {
		this.unitCategories = unitCategories;
	}

	public List<CodeListDTO> getCodeLists() {
		return codeLists;
	}

	public void setCodeLists(List<CodeListDTO> codeLists) {
		this.codeLists = codeLists;
	}

	public AnswersSaveDTO getAnswersSaveDTO() {
		return answersSaveDTO;
	}

	public void setAnswersSaveDTO(AnswersSaveDTO answersSaveDTO) {
		this.answersSaveDTO = answersSaveDTO;
	}

}
