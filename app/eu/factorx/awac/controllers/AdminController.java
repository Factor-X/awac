package eu.factorx.awac.controllers;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;

import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import eu.factorx.awac.InitializationThread;
import eu.factorx.awac.models.code.CodeList;
import eu.factorx.awac.service.CodeLabelService;
import eu.factorx.awac.util.data.importer.TranslationImporter;

@org.springframework.stereotype.Controller
public class AdminController extends Controller {

	@Autowired
	private CodeLabelService codeLabelService;

	@Transactional(readOnly = false)
	@Security.Authenticated(SecuredController.class)
	public Result resetTranslations() {
		// remove old translations code labels
		codeLabelService.removeCodeLabelsByList(CodeList.TRANSLATIONS_SURVEY, CodeList.TRANSLATIONS_INTERFACE, CodeList.TRANSLATIONS_ERROR_MESSAGES);
		// import new translations code labels
		new TranslationImporter(JPA.em().unwrap(Session.class)).run();
		// reset code labels cache
		codeLabelService.resetCache();
		// refresh InMemory data
		InitializationThread.createInMemoryTranslations();

		return (ok());
	}

}
