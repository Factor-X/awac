package eu.factorx.awac.dto.awac.get;

import eu.factorx.awac.dto.DTO;

import java.util.List;

public class UpdateFormsLabelsDTO extends DTO {

	private List<CalculatorItem> calculatorItems;

	public UpdateFormsLabelsDTO() {
	}

	public UpdateFormsLabelsDTO(List<CalculatorItem> calculatorItems) {
		this.calculatorItems = calculatorItems;
	}

	public List<CalculatorItem> getCalculatorItems() {
		return calculatorItems;
	}

	public void setCalculatorItems(List<CalculatorItem> calculatorItems) {
		this.calculatorItems = calculatorItems;
	}

	public static class CalculatorItem {

		public CalculatorItem() {
		}

		public CalculatorItem(FullCodeLabelDTO label, List<FormItem> forms) {
			this.label = label;
			this.forms = forms;
		}

		private FullCodeLabelDTO label;

		private List<FormItem> forms;

		public FullCodeLabelDTO getLabel() {
			return label;
		}

		public void setLabel(FullCodeLabelDTO label) {
			this.label = label;
		}

		public List<FormItem> getForms() {
			return forms;
		}

		public void setForms(List<FormItem> forms) {
			this.forms = forms;
		}
	}

	public static class FormItem {

		private FullCodeLabelDTO label;

		private List<QuestionSetItem> questionSets;

		public FormItem() {
		}

		public FormItem(FullCodeLabelDTO label, List<QuestionSetItem> questionSets) {
			this.label = label;
			this.questionSets = questionSets;
		}

		public FullCodeLabelDTO getLabel() {
			return label;
		}

		public void setLabel(FullCodeLabelDTO label) {
			this.label = label;
		}

		public List<QuestionSetItem> getQuestionSets() {
			return questionSets;
		}

		public void setQuestionSets(List<QuestionSetItem> questionSets) {
			this.questionSets = questionSets;
		}
	}

	public static class QuestionSetItem {

		private FullCodeLabelDTO label;

		private List<QuestionItem> questions;

		public FullCodeLabelDTO getLabel() {
			return label;
		}

		public void setLabel(FullCodeLabelDTO label) {
			this.label = label;
		}

		public List<QuestionItem> getQuestions() {
			return questions;
		}

		public void setQuestions(List<QuestionItem> questions) {
			this.questions = questions;
		}
	}

	public static class QuestionItem {

		private FullCodeLabelDTO label;

		public FullCodeLabelDTO getLabel() {
			return label;
		}

		public void setLabel(FullCodeLabelDTO label) {
			this.label = label;
		}
	}
}
