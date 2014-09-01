package eu.factorx.awac.util.data.importer.badImporter;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import java.io.StringWriter;

/**
 * Created by florian on 29/08/14.
 */
public class Template {
    private final VelocityContext velocityContext;
    private final TemplateName templateName;
    private final String fileName;

    public Template(TemplateName template, String fileName) {
        this.velocityContext = new VelocityContext();
        this.fileName = fileName;
        this.templateName = template;
    }


    public void addParameter(String key, String content) {
        velocityContext.put(key, content);
    }


    public void generate() {
        org.apache.velocity.Template template = Velocity.getTemplate(templateName.getUrl());

        StringWriter writer = new StringWriter();

        template.merge(velocityContext, writer);

        //TODO Function.save(writer.toString(), "files/results/", fileName, true);
    }
}
