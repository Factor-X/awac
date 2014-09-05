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
import scala.Option;
import scala.collection.mutable.*;

import java.util.Map;



@Component
public class VelocityGeneratorImpl implements VelocityGeneratorService {

	private String ROOT = "app/eu/factorx/awac/vm/";

	public Result ok(String templateName,Map values) {

		Html html = new Html(new scala.collection.mutable.StringBuilder(generate(templateName,values)));
		return Results.ok(html);
	}

	public String generate (String templateName,Map values) {
		StringBuffer html = new StringBuffer();
		html.append(package$.MODULE$.VM(ROOT + templateName, Scala.asScala(values), "utf-8"));
		Logger.info("HTML generated:" + html.toString());
		return(html.toString());
	}

	private String getCurrentFullMethodName() {
		return Thread.currentThread().getStackTrace()[2].getClassName() + "." + Thread.currentThread().getStackTrace()[2].getMethodName();
	}

	private String getCurrentMethodName() {
		return Thread.currentThread().getStackTrace()[2].getMethodName()+".vm";
	}

	public String getTemplateNameByMethodName() {
		return Thread.currentThread().getStackTrace()[2].getMethodName()+".vm";
	}

} // end of class
