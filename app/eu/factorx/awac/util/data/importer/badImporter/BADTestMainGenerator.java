package eu.factorx.awac.util.data.importer.badImporter;

import eu.factorx.awac.service.CodeLabelService;
import eu.factorx.awac.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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


    public void generateBAD(List<BAD> bads, TemplateName templateName) {

        List<BAD> result = new ArrayList<>();
        for (BAD bad : bads) {
            if (bad.getTestValues().size() > 0) {
                result.add(bad);
            }
        }


        //create template
        BADTemplate badTemplate = new BADTemplate("public/template/badTestMain.vm", "BadTest.java");

        //inset questions
        badTemplate.addParameter("bads", result);

        //insert pakcage
        badTemplate.addParameter("PACKAGE", templateName.getPackageTest());

        badTemplate.generate(templateName.getTestPath());

    }

}
