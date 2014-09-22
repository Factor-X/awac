/*
 *
 * Instant Play Framework
 * AWAC
 *                       
 *
 * Copyright (c) 2014 Factor-X.
 * Author Gaston Hollands
 *
 */

package eu.factorx.awac.service.impl;


import eu.factorx.awac.service.VelocityGeneratorService;
import jp.furyu.play.velocity.mvc.package$;
import org.springframework.stereotype.Component;
import play.Logger;
import play.api.Play;
import play.api.templates.Html;
import play.libs.Scala;
import play.mvc.Result;
import play.mvc.Results;


import java.io.InputStream;
import java.util.Map;

@Component
public class VelocityGeneratorImpl implements VelocityGeneratorService {

	private String ROOT = "public/vm/";

	public Result ok(String templateName, Map values) {

		Html html = new Html(new scala.collection.mutable.StringBuilder(generate(templateName, values)));
		return Results.ok(html);
	}

	public String generate(String templateName, Map values) {


		String path = Play.current().path().getPath();
		Logger.info("Play path :" + path);

		String absolutePath = Play.current().path().getAbsolutePath();
		Logger.info("Play absolute path :" + absolutePath);

		try	{
			String canonicalPath = Play.current().path().getCanonicalPath();
			Logger.info("Play canocical path :" + canonicalPath);
		} catch (Exception e) {
			e.printStackTrace();
		}


		//Play.classloader(play.api.Play.current()).getResource("any_file");

		Logger.info("Play before input stream...");
		InputStream in = Play.classloader(play.api.Play.current()).getResourceAsStream(ROOT+templateName);
		Logger.info("Play before serverPath...");

		String serverPath = "";

		if (play.Play.isDev()) {
			serverPath = Play.classloader(play.api.Play.current()).getResource(ROOT + templateName).toString().replace("file:", "");
		} else {
			serverPath = Play.classloader(play.api.Play.current()).getResource(ROOT + templateName).toString().replace("file:", "");
			serverPath = serverPath.replace("jar:", "");
		}

		Logger.info("Play original server Path :" + serverPath);
		//String serverPath = Play.classloader(play.api.Play.current()).getResource("public/vm/launchInvitation.vm").getPath();
		serverPath = serverPath.replace(Play.current().path().getAbsolutePath(),"");

		Logger.info("Play input stream :" + in);
		Logger.info("Play replaced server Path :" + serverPath);


		StringBuffer html = new StringBuffer();
		//html.append(package$.MODULE$.VM(ROOT + templateName, Scala.asScala(values), "utf-8"));
		html.append(package$.MODULE$.VM(serverPath, Scala.asScala(values), "utf-8"));
		//Logger.info("HTML generated:" + html.toString());
		return (html.toString());
	}

	private String getCurrentFullMethodName() {
		return Thread.currentThread().getStackTrace()[2].getClassName() + "." + Thread.currentThread().getStackTrace()[2].getMethodName();
	}

	private String getCurrentMethodName() {
		return Thread.currentThread().getStackTrace()[2].getMethodName() + ".vm";
	}

	public String getTemplateNameByMethodName() {
		return Thread.currentThread().getStackTrace()[2].getMethodName() + ".vm";
	}

} // end of class
