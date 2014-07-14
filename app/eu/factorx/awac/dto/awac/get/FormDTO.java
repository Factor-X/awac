package eu.factorx.awac.dto.awac.get;

import java.util.List;

import eu.factorx.awac.dto.DTO;
import eu.factorx.awac.dto.awac.post.AnswersSaveDTO;

public class FormDTO extends DTO {

	private List<QuestionDTO> questions;

	private List<UnitCategoryDTO> unitCategories;

    private List<CodeListDTO> codeLists;

	private AnswersSaveDTO answersSaveDTO;

	public FormDTO() {
		super();
	}

    public FormDTO(List<QuestionDTO> questions, List<UnitCategoryDTO> unitCategories, List<CodeListDTO> codeLists, AnswersSaveDTO answersSaveDTO) {
        this.questions = questions;
        this.unitCategories = unitCategories;
        this.codeLists = codeLists;
        this.answersSaveDTO = answersSaveDTO;
    }

    public List<QuestionDTO> getQuestions() {
		return questions;
	}

	public void setQuestions(List<QuestionDTO> questions) {
		this.questions = questions;
	}

	public List<UnitCategoryDTO> getUnitCategories() {
		return unitCategories;
	}

	public void setUnitCategories(List<UnitCategoryDTO> unitCategories) {
		this.unitCategories = unitCategories;
	}

	public AnswersSaveDTO getAnswersSaveDTO() {
		return answersSaveDTO;
	}

	public void setAnswersSaveDTO(AnswersSaveDTO answersSaveDTO) {
		this.answersSaveDTO = answersSaveDTO;
	}

    public List<CodeListDTO> getCodeLists() {
        return codeLists;
    }

    public void setCodeLists(List<CodeListDTO> codeLists) {
        this.codeLists = codeLists;
    }
}