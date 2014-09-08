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

package eu.factorx.awac.service;

import play.api.templates.Html;
import play.mvc.Result;

import java.io.OutputStream;
import java.util.Map;

public interface VelocityGeneratorService {

	public Result ok(String templateName,Map values);
	public String generate(String templateName,Map values);
	public String getTemplateNameByMethodName();

}
