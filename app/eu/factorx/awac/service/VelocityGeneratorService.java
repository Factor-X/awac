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

import java.util.Map;

import play.mvc.Result;

public interface VelocityGeneratorService {

	public Result ok(String templateName, Map values);

	public String generate(String templateName, Map values);

	public String getTemplateNameByMethodName();

}
