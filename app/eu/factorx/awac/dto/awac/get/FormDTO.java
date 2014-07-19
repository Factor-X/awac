package eu.factorx.awac.dto.awac.get;

import java.util.List;
import java.util.Map;

import eu.factorx.awac.dto.DTO;
import eu.factorx.awac.dto.awac.post.QuestionAnswersDTO;
import eu.factorx.awac.dto.validation.annotations.NotNull;

public class FormDTO extends DTO {

	/**
	 * K: unitCategoryId
	 */
	private Map<Long, UnitCategoryDTO> unitCategories;

	/**
	 * K: codeListName
	 */
	private Map<String, CodeListDTO> codeLists;

	@NotNull
	/**
	 * contains list of questionAnswerDTO
	 * each DTO contains all the structure of the QuestionSetAnswer, with value the response
	 */
	private List<QuestionSetDTO> questionSets;

	private QuestionAnswersDTO answersSave;

    public FormDTO() {
    }

    public FormDTO(Map<Long, UnitCategoryDTO> unitCategories, Map<String, CodeListDTO> codeLists, List<QuestionSetDTO> questionSets, QuestionAnswersDTO answersSave) {
        this.unitCategories = unitCategories;
        this.codeLists = codeLists;
        this.questionSets = questionSets;
        this.answersSave = answersSave;
    }

    public Map<Long, UnitCategoryDTO> getUnitCategories() {
        return unitCategories;
    }

    public void setUnitCategories(Map<Long, UnitCategoryDTO> unitCategories) {
        this.unitCategories = unitCategories;
    }

    public Map<String, CodeListDTO> getCodeLists() {
        return codeLists;
    }

    public void setCodeLists(Map<String, CodeListDTO> codeLists) {
        this.codeLists = codeLists;
    }

    public List<QuestionSetDTO> getQuestionSets() {
        return questionSets;
    }

    public void setQuestionSets(List<QuestionSetDTO> questionSets) {
        this.questionSets = questionSets;
    }

    public QuestionAnswersDTO getAnswersSave() {
        return answersSave;
    }

    public void setAnswersSave(QuestionAnswersDTO answersSave) {
        this.answersSave = answersSave;
    }
}
