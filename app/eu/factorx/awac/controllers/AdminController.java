package eu.factorx.awac.controllers;

import java.util.HashMap;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;

import play.Logger;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import eu.factorx.awac.InMemoryData;
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

		// reset code labels cache
		codeLabelService.resetCache();

		// remove old translations code labels
		removeCodeLabelsByList(CodeList.TRANSLATIONS_SURVEY);
		removeCodeLabelsByList(CodeList.TRANSLATIONS_INTERFACE);
		removeCodeLabelsByList(CodeList.TRANSLATIONS_ERROR_MESSAGES);

		// import new translations code labels
		new TranslationImporter(JPA.em().unwrap(Session.class)).run();

		// refresh InMemory data
		InMemoryData.translations = new HashMap<>();
		InitializationThread.createInMemoryTranslations();
		
		return(ok());
	}

	private void removeCodeLabelsByList(CodeList codeList) {
		int nbRemoved = codeLabelService.removeCodeLabelsByList(codeList);
		Logger.info("Removed {} code labels of list '{}'", nbRemoved, codeList);
	}
}
