package eu.factorx.awac.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import play.api.templates.Html;
import play.mvc.Controller;
import scala.collection.mutable.StringBuilder;

import com.fasterxml.jackson.databind.JsonNode;

import eu.factorx.awac.dto.DTO;
import eu.factorx.awac.dto.SvgContent;
import eu.factorx.awac.dto.awac.get.CodeLabelDTO;
import eu.factorx.awac.dto.awac.get.CodeListDTO;
import eu.factorx.awac.models.code.CodeList;
import eu.factorx.awac.models.code.label.CodeLabel;
import eu.factorx.awac.models.code.type.LanguageCode;
import eu.factorx.awac.service.CodeLabelService;
import eu.factorx.awac.util.BusinessErrorType;
import eu.factorx.awac.util.MyrmexRuntimeException;

@org.springframework.stereotype.Controller
public class AbstractController extends Controller {

	@Autowired
	protected SecuredController securedController;

	@Autowired
	protected CodeLabelService codeLabelService;

	protected static <T extends DTO> T extractDTOFromRequest(Class<T> DTOclass) {
		JsonNode json = request().body().asJson();
		T dto = DTO.getDTO(json, DTOclass);
		if (dto == null) {
			throw new MyrmexRuntimeException(BusinessErrorType.CONVERSION_DTO_ERROR, DTOclass.getName());
		}
		return dto;
	}

	protected static Html toHtml(String str) {
		scala.collection.mutable.StringBuilder sb = new StringBuilder(str);
		Html html = new Html(sb);
		return html;
	}

	protected static SvgContent toSvg(String str) {
		return new SvgContent(str);
	}

	protected void markNoCache() {
		response().setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
		response().setHeader("Pragma", "no-cache"); // HTTP 1.0.
	}

	protected List<CodeListDTO> getCodeListDTOs(CodeList... codeLists) {
		LanguageCode userLanguage = securedController.getDefaultLanguage();
		List<CodeListDTO> res = new ArrayList<>();
		for (CodeList codeList : codeLists) {
			res.add(toCodeListDTO(codeList, userLanguage));
		}
		return res;
	}

	protected CodeListDTO toCodeListDTO(CodeList codeList, LanguageCode lang) {
		List<CodeLabel> codeLabels = new ArrayList<>(codeLabelService.findCodeLabelsByList(codeList).values());
		List<CodeLabelDTO> codeLabelDTOs = new ArrayList<>();
		for (CodeLabel codeLabel : codeLabels) {
			codeLabelDTOs.add(new CodeLabelDTO(codeLabel.getKey(), codeLabel.getLabel(lang)));
		}
		return new CodeListDTO(codeList.name(), codeLabelDTOs);
	}

}
