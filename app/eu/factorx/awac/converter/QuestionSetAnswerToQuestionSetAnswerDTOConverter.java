package eu.factorx.awac.converter;



public class QuestionSetAnswerToQuestionSetAnswerDTOConverter{/* implements
		Converter<QuestionSetAnswer, QuestionSetAnswerDTO> {

	@Autowired
	QuestionAnswerToAnswerLineConverter questionAnswerToAnswerLineConverter;

    @Autowired
    QuestionToQuestionDTOConverter questionToQuestionDTOConverter;

	@Override
	public QuestionSetAnswerDTO convert(QuestionSetAnswer questionSetAnswer) {
		QuestionSetAnswerDTO questionSetAnswerDTO = new QuestionSetAnswerDTO(questionSetAnswer.getId(),
				questionSetAnswer.getQuestionSet().getCode().getKey(), questionSetAnswer.getRepetitionIndex());
		for (QuestionAnswer questionAnswer : questionSetAnswer.getQuestionAnswers()) {

            QuestionAnswerDTO questionAnswerDTO = new QuestionAnswerDTO();
            questionAnswerDTO.setAnswerLine(convertQuestionAnswer(questionAnswer));
            questionAnswerDTO.setQuestionDTO(questionToQuestionDTOConverter.convert(questionAnswer.getQuestion()));

			questionSetAnswerDTO.getQuestionAnswers().add(questionAnswerDTO);
		}
		for (QuestionSetAnswer childQuestionSetAnswer : questionSetAnswer.getChildren()) {
			questionSetAnswerDTO.getChildren().add(convert(childQuestionSetAnswer));
		}
		return questionSetAnswerDTO;
	}

	private AnswerLine convertQuestionAnswer(QuestionAnswer questionAnswer) {
		return questionAnswerToAnswerLineConverter.convert(questionAnswer);
	}
*/
}
