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
package eu.factorx.awac.controllers;

import static play.data.Form.form;

//for XML
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import eu.factorx.awac.service.AdministratorService;
import org.springframework.beans.factory.annotation.Autowired;

import play.data.Form;
// for JSON
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import eu.factorx.awac.models.account.Administrator;
import eu.factorx.awac.service.PersonService;
// for PDF
import eu.factorx.awac.util.pdf.PDF;

/**
 * Manage a database of administratros
 */

@Security.Authenticated(SecuredController.class)
public class Administrators extends Controller {

	@Autowired
	private PersonService personService;
    @Autowired
    private AdministratorService administratorService;
    @Autowired
    private SecuredController securedController;

	/**
	 * This result directly redirect to application home.
	 */
	public Result GO_HOME = redirect(eu.factorx.awac.controllers.routes.Administrators.list(0, "identifier",
			"asc", ""));

	/**
	 * Handle default path requests, redirect to administrator list
	 */
	public Result index() {
		return GO_HOME;
	}

	/**
	 * Display the paginated list of administrators.
	 * 
	 * @param page
	 *            Current page number (starts from 0)
	 * @param sortBy
	 *            Column to be sorted
	 * @param order
	 *            Sort order (either asc or desc)
	 * @param filter
	 *            Filter applied on administrators names
	 */
	public Result list(int page, String sortBy, String order, String filter) {
		return ok(eu.factorx.awac.views.html.administrator.list.render(administratorService.findAll(), sortBy, order, filter));
	}

	/**
	 * Display list of administrators. for PDF output
	 * 
	 */
	public Result asPdf() {
		return PDF.ok(eu.factorx.awac.views.html.administrator.document.render(administratorService.findAll()));
	}

	/**
	 * Display list of administrators. for JSON output
	 * 
	 */
	public Result asJson() {
		return ok(Json.toJson(administratorService.findAll()));
		// return TODO;
	}

	/**
	 * Display list of administrators. for XML output
	 * 
	 */
	public Result asXml() {
		try {
			JAXBContext context = JAXBContext.newInstance(Administrator.class);
			Marshaller marshaller = context.createMarshaller();

			// Use linefeeds and indentation in the outputted XML
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			marshaller.marshal(administratorService.findById(new Long(1)), System.out);
		} catch (JAXBException jaxbe) {
			play.Logger.debug("JAXB Exception : " + jaxbe);
			return (GO_HOME);
		}

		return ok(Json.toJson(administratorService.findAll()));
		// return TODO;
	}

	/**
	 * Display the 'edit form' of a existing Administrator.
	 * 
	 * @param id
	 *            Id of the administrator to edit
	 */
	public Result edit(Long id) {
		if (securedController.isAdministrator()) {
			Form<Administrator> administratorForm = form(Administrator.class).fill(administratorService.findById(id));
			return ok(eu.factorx.awac.views.html.administrator.editForm.render(id, administratorForm));
		} else {
			return forbidden();
		}
	}

	/**
	 * Handle the 'edit form' submission
	 * 
	 * @param id
	 *            Id of the administrator to edit
	 */
	public Result update(Long id) {
		if (securedController.isAdministrator()) {
			Form<Administrator> administratorForm = form(Administrator.class).bindFromRequest();

			if (administratorForm.hasErrors()) {
				return badRequest(eu.factorx.awac.views.html.administrator.editForm.render(id, administratorForm));
			}

			// Automatic binding does not work for embedded objects.
			// administratorForm.get().update(id);

			// use manuel settings
			// Automatic binding does not work for embedded objects.
			// use manuel binding.
			Administrator adm = administratorForm.get();

			// basicaly getter and setter are generated by Play
			// This gives a strange way to affect/assign data on the code
			// But play uses internally getters and setter to keep encapsulation
			play.Logger.debug("Person ID:" + adm.getId());
			play.Logger.debug("LastName:" + adm.getLastname());
			play.Logger.debug("FirstName:" + adm.getFirstname());
			play.Logger.debug("Identifier:" + adm.getIdentifier());
			play.Logger.debug("Password:" + adm.getPassword());
			play.Logger.debug("Street:" + adm.getAddress().getStreet());
			play.Logger.debug("PostalCode:" + adm.getAddress().getPostalcode());
			play.Logger.debug("City:" + adm.getAddress().getCity());
			play.Logger.debug("Country:" + adm.getAddress().getCountry());

			Administrator updateAdministrator = administratorService.findById(id);

			updateAdministrator.setIdentifier(adm.getIdentifier());
			updateAdministrator.setPassword(adm.getPassword());
			updateAdministrator.setFirstname(adm.getFirstname());
			updateAdministrator.setLastname(adm.getLastname());
			updateAdministrator.setEmail(adm.getEmail());

			updateAdministrator.getAddress().setStreet(adm.getAddress().getStreet());
			updateAdministrator.getAddress().setPostalcode(adm.getAddress().getPostalcode());
			updateAdministrator.getAddress().setCity(adm.getAddress().getCity());
			updateAdministrator.getAddress().setCountry(adm.getAddress().getCountry());

			administratorService.update(updateAdministrator);

			flash("success", "Administrator " + administratorForm.get().getFirstname() + " has been updated");
			return GO_HOME;
		} else {
			return forbidden();
		}
	}

	/**
	 * Display the 'new administrator form'.
	 */
	public Result create() {
		Form<Administrator> administratorForm = form(Administrator.class);
		return ok(eu.factorx.awac.views.html.administrator.createForm.render(administratorForm));
	}

	/**
	 * Handle the 'new administrator form' submission
	 */
	public Result save() {

        Form<Administrator> administratorForm = form(Administrator.class).bindFromRequest();
		if (administratorForm.hasErrors()) {
			return badRequest(eu.factorx.awac.views.html.administrator.createForm.render(administratorForm));
		}

       // administratorService.save()
		administratorService.save(administratorForm.get());
		flash("success", "Administrator " + administratorForm.get().getFirstname()+ " has been created");
		return GO_HOME;
	}

	/**
	 * Handle administrator deletion
	 */
	public Result delete(Long id) {
		administratorService.remove(administratorService.findById(id));
		flash("success", "Administrator has been deleted");
		return GO_HOME;
	}
}