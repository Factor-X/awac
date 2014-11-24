package eu.factorx.awac.dto.awac.get;

import eu.factorx.awac.dto.DTO;

import java.util.ArrayList;
import java.util.List;

public class FormLabelsDTO extends DTO {

	private String codeKey;

	private String calculatorCodeKey;

	private List<QuestionSetItem> questionSets;

	public FormLabelsDTO() {
	}

	public FormLabelsDTO(String codeKey, String calculatorCodeKey) {
		this.calculatorCodeKey = calculatorCodeKey;
		this.codeKey = codeKey;
		this.questionSets = new ArrayList<>();
	}

	public String getCodeKey() {
		return codeKey;
	}

	public void setCodeKey(String codeKey) {
		this.codeKey = codeKey;
	}

	public String getCalculatorCodeKey() {
		return calculatorCodeKey;
	}

	public void setCalculatorCodeKey(String calculatorCodeKey) {
		this.calculatorCodeKey = calculatorCodeKey;
	}

	public List<QuestionSetItem> getQuestionSets() {
		return questionSets;
	}

	public void setQuestionSets(List<QuestionSetItem> questionSets) {
		this.questionSets = questionSets;
	}

	public boolean addQuestionSet(QuestionSetItem questionSetItem) {
		return this.questionSets.add(questionSetItem);
	}

	public static class QuestionSetItem {

		private UpdateCodeLabelDTO label;

		private List<QuestionSetItem> children;

		private List<QuestionItem> questions;

		public QuestionSetItem() {
		}

		public QuestionSetItem(UpdateCodeLabelDTO label) {
			this.label = label;
			this.children = new ArrayList<>();
			this.questions = new ArrayList<>();
		}

		public UpdateCodeLabelDTO getLabel() {
			return label;
		}

		public void setLabel(UpdateCodeLabelDTO label) {
			this.label = label;
		}

		public List<QuestionSetItem> getChildren() {
			return children;
		}

		public void setChildren(List<QuestionSetItem> children) {
			this.children = children;
		}

		public List<QuestionItem> getQuestions() {
			return questions;
		}

		public void setQuestions(List<QuestionItem> questions) {
			this.questions = questions;
		}

		public boolean addChild(QuestionSetItem questionSetItem) {
			return this.children.add(questionSetItem);
		}

		public boolean addQuestion(QuestionItem questionItem) {
			return this.questions.add(questionItem);
		}
	}

	public static class QuestionItem {

		private UpdateCodeLabelDTO label;

		public QuestionItem() {
		}

		public QuestionItem(UpdateCodeLabelDTO label) {
			this.label = label;
		}

		public UpdateCodeLabelDTO getLabel() {
			return label;
		}

		public void setLabel(UpdateCodeLabelDTO label) {
			this.label = label;
		}
	}

}

