package eu.factorx.awac.controllers;

import org.springframework.stereotype.Component;

import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import eu.factorx.awac.util.pdf.PDF;
import eu.factorx.awac.util.pdf.SVG;
import eu.factorx.awac.views.html.pdf.document;
// for PDF

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