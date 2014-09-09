package eu.factorx.awac.controllers;

import eu.factorx.awac.common.actions.SecurityAnnotation;
import eu.factorx.awac.service.VelocityGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import play.db.jpa.Transactional;
import play.mvc.Result;
import play.mvc.Security;

import java.util.HashMap;
import java.util.Map;

//annotate as Spring Component
@Component
@Transactional(readOnly = true)
@Security.Authenticated(SecuredController.class)
@SecurityAnnotation(isAdmin = false, isSystemAdmin = false)
public class VelocityController extends AbstractController {

	@Autowired
	VelocityGeneratorService velocityGeneratorService;
	/**
	 * Handle the velocity template generation.
	 */
	public Result velocity () {

		Map values = new HashMap<String,Object>();
		values.put("name","My name is gaston");
		values.put("title","This is my titre");

		return velocityGeneratorService.ok(velocityGeneratorService.getTemplateNameByMethodName(),values);
	}
}