package eu.factorx.awac.util.data.importer.badImporter;

import eu.factorx.awac.service.CodeLabelService;
import eu.factorx.awac.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by florian on 4/09/14.
 */
@Component
@Scope("prototype")
public class BADTestMainGenerator {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private CodeLabelService codeLabelService;

    private List<String> listQuestionCodeAlreadyUsed = new ArrayList<>();

    public void generateBAD(List<BAD> bads, Map<String, Answer> mapAnswer, TemplateName templateName) {

        List<BAD> result = new ArrayList<>();
        for (BAD bad : bads) {
            if (bad.getTestValues().size() > 0) {
                result.add(bad);
            }
        }

        //create template
        BADTemplate badTemplate = new BADTemplate(templateName.getTestMainTemplate(), "BadTest.java");


        //inset questions
        //generate questionAndAnswer list
        List<QuestionAndAnswer> questionAndAnswerList = new ArrayList<>();

        for (BAD bad : bads) {


            for (String questionCode : bad.getListQuestions()) {
                if (!listQuestionCodeAlreadyUsed.contains(questionCode)) {
                    listQuestionCodeAlreadyUsed.add(questionCode);
                    if (!mapAnswer.containsKey(questionCode)) {
                        //logLine.addError("Cannot found the answer for the required question " + questionCode);
                    } else {
                        questionAndAnswerList.add(new QuestionAndAnswer(questionCode, mapAnswer.get(questionCode).getAnswerLines()));
                    }
                }

            }
        }


        badTemplate.addParameter("questions", questionAndAnswerList);


        //insert user
        badTemplate.addParameter("user", templateName.getUserIdentifier());

        //insert calculator type
        badTemplate.addParameter("calculatorType", templateName.getInterfaceTypeCode().getKey().toUpperCase());

        //inset questions
        badTemplate.addParameter("bads", result);

        //insert pakcage
        badTemplate.addParameter("PACKAGE", templateName.getPackageTest());

        badTemplate.generate(templateName.getTestPath());

    }


    public class QuestionAndAnswer {

        private String questionCode;

        private List<AnswerLine> answerLines;

        public QuestionAndAnswer(String questionCode, List<AnswerLine> answerLines) {
            this.questionCode = questionCode;
            this.answerLines = answerLines;
        }

        public String getQuestionCode() {
            return questionCode;
        }

        public void setQuestionCode(String questionCode) {
            this.questionCode = questionCode;
        }

        public List<AnswerLine> getAnswerLines() {
            return answerLines;
        }

        public void setAnswerLines(List<AnswerLine> answerLines) {
            this.answerLines = answerLines;
        }

        @Override
        public String toString() {
            return "QuestionAndAnswer{" +
                    "questionCode='" + questionCode + '\'' +
                    ", answerLines=" + answerLines +
                    '}';
        }
    }

}
