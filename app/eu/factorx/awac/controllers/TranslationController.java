package eu.factorx.awac.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import play.db.jpa.Transactional;
import play.mvc.Result;
import eu.factorx.awac.dto.myrmex.get.TranslationsDTO;
import eu.factorx.awac.models.code.CodeList;
import eu.factorx.awac.models.code.label.CodeLabel;
import eu.factorx.awac.models.code.type.LanguageCode;
import eu.factorx.awac.service.CodeLabelService;

@org.springframework.stereotype.Controller
public class TranslationController extends AbstractController {

	@Autowired
	private CodeLabelService codeLabelService;

	@Transactional(readOnly = true)
	public Result fetch(String language) {

		String lang = language.toUpperCase();
		TranslationsDTO translationsDTO = new TranslationsDTO(lang);

		if (LanguageCode.DUTCH.getKey().equals(lang)) {
			for (CodeLabel codeLabel : getTranslations()) {
				translationsDTO.put(codeLabel.getKey(), codeLabel.getLabelNl());
			}
		} else if (LanguageCode.FRENCH.getKey().equals(lang)) {
			for (CodeLabel codeLabel : getTranslations()) {
				translationsDTO.put(codeLabel.getKey(), codeLabel.getLabelFr());
			}
		} else {
			for (CodeLabel codeLabel : getTranslations()) {
				translationsDTO.put(codeLabel.getKey(), codeLabel.getLabelEn());
			}
		}

		return ok(translationsDTO);
	}

	private List<CodeLabel> getTranslations() {
		List<CodeLabel> translations = new ArrayList<>();
		translations.addAll(codeLabelService.findCodeLabelsByList(CodeList.TRANSLATIONS_SURVEY).values());
		translations.addAll(codeLabelService.findCodeLabelsByList(CodeList.TRANSLATIONS_INTERFACE).values());
		translations.addAll(codeLabelService.findCodeLabelsByList(CodeList.TRANSLATIONS_ERROR_MESSAGES).values());
		translations.addAll(codeLabelService.findCodeLabelsByList(CodeList.QUESTION).values());
		translations.addAll(codeLabelService.findCodeLabelsByList(CodeList.INDICATOR).values());
		translations.addAll(codeLabelService.findCodeLabelsByList(CodeList.ActivityCategory).values());
		translations.addAll(codeLabelService.findCodeLabelsByList(CodeList.ActivitySubCategory).values());
		translations.addAll(codeLabelService.findCodeLabelsByList(CodeList.ActivitySource).values());
		translations.addAll(codeLabelService.findCodeLabelsByList(CodeList.ActivityType).values());
		translations.addAll(codeLabelService.findCodeLabelsByList(CodeList.IndicatorCategory).values());
        translations.addAll(codeLabelService.findCodeLabelsByList(CodeList.OrganizationStructure).values());
		return translations;
	}

}
