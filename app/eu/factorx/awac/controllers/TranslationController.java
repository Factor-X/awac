package eu.factorx.awac.controllers;

import eu.factorx.awac.dto.admin.get.AvailableLanguagesDTO;
import eu.factorx.awac.dto.admin.get.WysiwygDocumentDTO;
import eu.factorx.awac.dto.myrmex.get.TranslationsDTO;
import eu.factorx.awac.models.code.CodeList;
import eu.factorx.awac.models.code.WysiwygDocument;
import eu.factorx.awac.models.code.label.CodeLabel;
import eu.factorx.awac.models.code.type.InterfaceTypeCode;
import eu.factorx.awac.models.code.type.LanguageCode;
import eu.factorx.awac.models.forms.AwacCalculator;
import eu.factorx.awac.service.AwacCalculatorService;
import eu.factorx.awac.service.CodeLabelService;
import eu.factorx.awac.service.WysiwygDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import play.Logger;
import play.db.jpa.Transactional;
import play.mvc.Http.Context;
import play.mvc.Result;

import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Controller
public class TranslationController extends AbstractController {

    @Autowired
    private CodeLabelService       codeLabelService;
    @Autowired
    private WysiwygDocumentService wysiwygDocumentService;
    @Autowired
    private ConversionService      conversionService;
    @Autowired
    private AwacCalculatorService  awacCalculatorService;

    @Transactional(readOnly = true)
    public Result fetch(String language) {
        String lang = language.toUpperCase();
        Context.current().session().put(SecuredController.SESSION_DEFAULT_LANGUAGE_STORE, language.toUpperCase());

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
        return translations;
    }

    @Transactional(readOnly = true)
    public Result getWysiwygDocument(String category, String name) {
        WysiwygDocument wysiwygDocument = wysiwygDocumentService.findByCategoryAndName(category, name);
        return ok(conversionService.convert(wysiwygDocument, WysiwygDocumentDTO.class));
    }

    @Transactional(readOnly = true)
    public Result getAvailableLanguages(String calculatorName) {

        AwacCalculator awacCalculator = awacCalculatorService.findByCode(new InterfaceTypeCode(calculatorName));

        AvailableLanguagesDTO result = new AvailableLanguagesDTO(awacCalculator.isFrEnabled(), awacCalculator.isNlEnabled(), awacCalculator.isEnEnabled());

        return ok(result);
    }


}
