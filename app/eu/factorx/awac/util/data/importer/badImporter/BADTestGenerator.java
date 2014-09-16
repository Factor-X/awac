package eu.factorx.awac.util.data.importer.badImporter;

import eu.factorx.awac.models.code.type.QuestionCode;
import eu.factorx.awac.models.data.question.Question;
import eu.factorx.awac.models.data.question.QuestionSet;
import eu.factorx.awac.service.CodeLabelService;
import eu.factorx.awac.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import play.Logger;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by florian on 4/09/14.
 */
@Component
@Scope("prototype")
public class BADTestGenerator {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private CodeLabelService codeLabelService;


    public void generateBAD(BAD bad, BADLog.LogLine logLine, Map<String, Answer> mapAnswer) {

        if(bad.getTestValues().size()>0) {

            //generate questionAndAnswer list
            List<QuestionAndAnswer> questionAndAnswerList = new ArrayList<>();

            for (String questionCode : bad.getListQuestions()) {
                if (mapAnswer.get(questionCode) == null) {
                    logLine.addError("Cannot found the answer for the required question " + questionCode);
                } else {
                    questionAndAnswerList.add(new QuestionAndAnswer(questionCode, mapAnswer.get(questionCode).getAnswerLines()));
                }
            }

            //create template
            BADTemplate badTemplate = new BADTemplate(TemplateName.BAD_TEST, "BAD_" + bad.getBaseActivityDataCode() + "Test.java");

            Logger.info("questionAndAnswerList for " + bad.getBaseActivityDataCode() + "->" + questionAndAnswerList.toString());

            //inset questions
            badTemplate.addParameter("questions", questionAndAnswerList);

            //insert answer
            badTemplate.addParameter("testValues", bad.getTestValues());

            //insert bad
            badTemplate.addParameter("bad", bad);

            badTemplate.generate(TemplateName.BAD_TEST.getTargetPath());
        }
        else{
            logLine.addWarn("The test cannot be generated because there is not correct value available");
        }
    }


    public class QuestionAndAnswer{

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