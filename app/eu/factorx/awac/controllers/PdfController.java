package eu.factorx.awac.controllers;

import eu.factorx.awac.common.actions.SecurityAnnotation;
import eu.factorx.awac.util.email.messages.EmailMessage;
import eu.factorx.awac.util.email.service.EmailService;
import eu.factorx.awac.util.pdf.PDF;
import eu.factorx.awac.util.pdf.SVG;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import play.Logger;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

// for PDF
import eu.factorx.awac.util.pdf.PDF;
import eu.factorx.awac.views.html.pdf.*;

//annotate as Spring Component
@Component
@Transactional(readOnly = true)
//@Security.Authenticated(SecuredController.class)
//@SecurityAnnotation(isAdmin = false, isSystemAdmin = false)
public class PdfController extends Controller {

	/**
	 * for PDF output
	 *
	 */
	public static Result asPdf() {
		return PDF.ok(document.render("this is my message"));
	}

	/**
	 *
	 * using SVG data
	 *
	 */

	public static Result withSvg() {
		SVG.toPDF();
		//return PDF.ok(svg.render("this is a PDF with SVG"));
		return (ok());
	}

}