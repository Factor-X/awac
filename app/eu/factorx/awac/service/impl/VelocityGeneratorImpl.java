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
import org.apache.commons.io.IOUtils;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.stereotype.Component;
import play.Logger;
import play.api.Play;
import play.api.templates.Html;
import play.libs.Scala;
import play.mvc.Result;
import play.mvc.Results;


import java.io.*;
import java.util.Iterator;
import java.util.Map;

@Component
public class VelocityGeneratorImpl implements VelocityGeneratorService {

	private String ROOT = "public/vm/";
	private String ROOT_PROD = "/app/public/vm/";

	public Result ok(String templateName, Map values) {

		Html html = new Html(new scala.collection.mutable.StringBuilder(generate(templateName, values)));
		return Results.ok(html);
	}

	public String generate(String templateName, Map values) {

		InputStream in = Play.classloader(play.api.Play.current()).getResourceAsStream(ROOT+templateName);
		String velocityTemplate= new String("");

		try {
			velocityTemplate = IOUtils.toString(in, "UTF8");
		} catch (Exception e) {
			e.printStackTrace();
		}

		Logger.info("Velocity Template Content : " + velocityTemplate);

//		String serverPath = "";
//
//		if (play.Play.isDev()) {
//			serverPath = Play.classloader(play.api.Play.current()).getResource(ROOT + templateName).toString().replace("file:", "");
//		} else {
//			serverPath = Play.classloader(play.api.Play.current()).getResource(ROOT + templateName).toString().replace("file:", "");
//			serverPath = serverPath.replace("jar:", "");
//			serverPath = serverPath.replace("!", "");
//		}
//
//		Logger.info("Play original server Path :" + serverPath);
//		//String serverPath = Play.classloader(play.api.Play.current()).getResource("public/vm/launchInvitation.vm").getPath();
//		serverPath = serverPath.replace(Play.current().path().getAbsolutePath(),"");
//
//		Logger.info("Play input stream :" + in);
//		Logger.info("Play replaced server Path :" + serverPath);


//		Logger.info("velocity return: " + velocityRender(theString,values));

//		StringBuffer html = new StringBuffer();
//		//html.append(package$.MODULE$.VM(ROOT + templateName, Scala.asScala(values), "utf-8"));
//		if (play.Play.isDev()) {
//			html.append(package$.MODULE$.VM(serverPath, Scala.asScala(values), "utf-8"));
//		} else {
//			html.append(package$.MODULE$.VM(ROOT_PROD + templateName , Scala.asScala(values), "utf-8"));
//		}

		//Logger.info("HTML generated:" + html.toString());

		return (velocityRender(velocityTemplate,values));
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

	private String velocityRender (String template,Map values) {

		StringBuffer Html = new StringBuffer();

		VelocityEngine ve = new VelocityEngine();
		ve.init();

		VelocityContext context = new VelocityContext();

		Iterator it = values.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry)it.next();
			context.put(entry.getKey().toString(), entry.getValue());
		}

		ByteArrayOutputStream output = new ByteArrayOutputStream();
		OutputStreamWriter html = new OutputStreamWriter(output);
		//OutputStream html = new OutputStream(output);

		StringWriter result = new StringWriter();
		try {
			if (!ve.evaluate(context, result, "report", template)) {
				Logger.info("Evaluation error");
			} else {
				Logger.info("Evaluation success");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		Logger.info("Render result:" + result.toString());
		return (result.toString());

	}

} // end of class
