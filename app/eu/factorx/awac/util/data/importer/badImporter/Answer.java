package eu.factorx.awac.util.data.importer.badImporter;

import eu.factorx.awac.models.data.question.Question;
import eu.factorx.awac.models.data.question.QuestionSet;
import play.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by florian on 11/09/14.
 */
public class Answer {
    private Question question;

    List<AnswerLine>  answerLines = new ArrayList<>();

    public Answer(Question question) {
        this.question=question;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public List<AnswerLine> getAnswerLines() {
        return answerLines;
    }

    public void setAnswerLines(List<AnswerLine> answerLines) {
        this.answerLines = answerLines;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "question=" + question +
                ", answerLines=" + answerLines +
                '}';
    }

    public void addAnswerLine(AnswerLine answerLine) {
        this.answerLines.add(answerLine);
    }
}
