package eu.factorx.awac.dto.awac.get;

import java.util.List;
import java.util.Map;

import eu.factorx.awac.dto.DTO;
import eu.factorx.awac.dto.validation.annotations.NotNull;

public class FormDTO extends DTO {

	@NotNull
    /**
     * define the scopeId
     */
	private Long scopeId;

	@NotNull
    /**
     * define the period
     */
	private Long periodId;

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
	private List<QuestionSetAnswerDTO> listAnswers;

	public FormDTO() {
		super();
	}

	/**
	 * @param scopeId
	 * @param periodId
	 * @param unitCategories
	 * @param codeLists
	 * @param listAnswers
	 */
	public FormDTO(Long scopeId, Long periodId, Map<Long, UnitCategoryDTO> unitCategories,
			Map<String, CodeListDTO> codeLists, List<QuestionSetAnswerDTO> listAnswers) {
		super();
		this.scopeId = scopeId;
		this.periodId = periodId;
		this.unitCategories = unitCategories;
		this.codeLists = codeLists;
		this.listAnswers = listAnswers;
	}

	public Long getScopeId() {
		return scopeId;
	}

	public void setScopeId(Long scopeId) {
		this.scopeId = scopeId;
	}

	public Long getPeriodId() {
		return periodId;
	}

	public void setPeriodId(Long periodId) {
		this.periodId = periodId;
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

	public List<QuestionSetAnswerDTO> getListAnswers() {
		return listAnswers;
	}

	public void setListAnswers(List<QuestionSetAnswerDTO> listAnswers) {
		this.listAnswers = listAnswers;
	}

}
