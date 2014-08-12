package eu.factorx.awac.controllers.jade;

/**
 * Created by gaston on 8/8/14.
 */
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import play.api.templates.Html;
import play.libs.Scala;
import play.mvc.Controller;
import de.neuland.jade4j.Jade4J;
import de.neuland.jade4j.JadeConfiguration;
import de.neuland.jade4j.template.FileTemplateLoader;
import de.neuland.jade4j.template.JadeTemplate;

public class JadeBaseController extends Controller {
	private static final JadeConfiguration jade = new JadeConfiguration();

	static {
		jade.setTemplateLoader(new FileTemplateLoader("app/eu/factorx/awac/views/", "UTF-8"));
		jade.setMode(Jade4J.Mode.HTML);
		jade.setPrettyPrint(true);
	}

	/**
	 * Convenience method for rendering a template by name (without .jade
	 * extension).
	 */
	public static Html render(String template) {
		return render(template, new HashMap<String, Object>());
	}

	/**
	 * Convenience method for rendering a template by name (without .jade
	 * extension) and some context variables.
	 */
	public static Html render(String template, Map<String, Object> context) {
		try {
			JadeTemplate jadeTemplate = jade.getTemplate(template);
			String buffer = jade.renderTemplate(jadeTemplate, context);
			scala.collection.mutable.StringBuilder sb = new scala.collection.mutable.StringBuilder ();
			sb.append(buffer);
			return new Html(sb);
		}
		catch(IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Convenience method for rendering a template by name (without .jade
	 * extension) and some context variables pass as varargs as pairs of
	 * [name] [value] arguments.
	 */
	public static Html render(String template, Object... contextArgs) {
		assert contextArgs.length >=2 && contextArgs.length % 2 == 0;
		Map<String, Object> context = new HashMap<String, Object>();
		for (int i = 0; i < contextArgs.length; i += 2) {
			context.put((String)contextArgs[i], contextArgs[i + 1]);
		}
		return render(template, context);
	}
}
