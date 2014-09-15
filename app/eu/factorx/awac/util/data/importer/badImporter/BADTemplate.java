package eu.factorx.awac.util.data.importer.badImporter;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import play.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by florian on 29/08/14.
 */
public class BADTemplate {


    private final VelocityContext velocityContext;
    private final TemplateName templateName;
    private final String fileName;

    public BADTemplate(TemplateName template, String fileName) {
        this.velocityContext = new VelocityContext();
        this.fileName = fileName;
        this.templateName = template;
    }



    public void addParameter(String key, List<?> content) {
        if (content == null) {
            velocityContext.put(key, new ArrayList<>());
        } else {
            velocityContext.put(key, content);
        }
    }

    public void addParameter(String key, String content) {
        if (content == null) {
            velocityContext.put(key, "null");
        } else {
            velocityContext.put(key, content);
        }
    }

    public void addParameter(String key, boolean content) {
        velocityContext.put(key, content);
    }


    public void addParameter(String key, Object object) {
        velocityContext.put(key,object);
    }

    public void generate(String path) {
        org.apache.velocity.Template template = Velocity.getTemplate(templateName.getUrl());

        StringWriter writer = new StringWriter();

        template.merge(velocityContext, writer);

        save(writer.toString(), path, fileName, true);
    }

    public static void save(final String text, final String path, final String name, boolean erase) {

        //Logger.info("Try to save " + name + " at " + path + ".. ");

        final String nameFile = path + name;

        final File file = new File(nameFile);

        if (!file.exists() || erase == true) {

            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            try {
                file.createNewFile();
                //System.out.println("saved !");
            } catch (final IOException e) {
                //System.out.println("file no created");
            }

            file.setWritable(true);
            file.setReadable(true);
            try {
                //final PrintWriter out = new PrintWriter(new FileWriter(nameFile), true);
                Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(nameFile), "UTF-8"));

                out.write(text);

                out.close();
            } catch (final Exception e) {
                e.printStackTrace();
            }
        }

    }

}
