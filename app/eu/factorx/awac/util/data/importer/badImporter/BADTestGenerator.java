package eu.factorx.awac.util.data.importer.badImporter;

import eu.factorx.awac.models.code.type.QuestionCode;
import eu.factorx.awac.models.data.question.Question;
import eu.factorx.awac.models.data.question.QuestionSet;
import eu.factorx.awac.service.CodeLabelService;
import eu.factorx.awac.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

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

        //create template
        BADTemplate badTemplate = new BADTemplate(TemplateName.BAD_TEST, "BAD" + bad.getBaseActivityDataCode() + "Test.java");

        //inset questions
        badTemplate.addParameter("questions",bad.getListQuestions());

        //insert bad
        badTemplate.addParameter("bad", bad);

        //badTemplate.generate(TemplateName.BAD_TEST.getTargetPath());
    }

}
