package eu.factorx.awac.util.data.importer.badImporter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import play.Logger;
import eu.factorx.awac.service.CodeLabelService;
import eu.factorx.awac.service.QuestionService;

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


    public void generateBAD(BAD bad, BADLog.LogLine logLine, TemplateName templateName) {


        if (bad.getTestValues().size() > 0) {

            //create template
            BADTemplate badTemplate = new BADTemplate(templateName.getTestTemplate(), "BAD_" + bad.getBaseActivityDataCode() + "Test.java");


            //insert user
            badTemplate.addParameter("user", templateName.getUserIdentifier());

            //path
            badTemplate.addParameter("PACKAGE", templateName.getPackageTest());

            //bad package
            badTemplate.addParameter("BAD_PACKAGE", templateName.getPackageString());

            //insert answer
            badTemplate.addParameter("testValues", bad.getTestValues());

            //insert bad
            badTemplate.addParameter("bad", bad);

            badTemplate.generate(templateName.getTestPath());
        } else {
            logLine.addWarn("The test cannot be generated because there is not correct value available");
        }
    }


}
