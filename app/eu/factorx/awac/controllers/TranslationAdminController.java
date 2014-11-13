package eu.factorx.awac.controllers;

import eu.factorx.awac.dto.awac.get.CodeListDTO;
import eu.factorx.awac.dto.awac.shared.ListDTO;
import eu.factorx.awac.dto.awac.shared.SubListDTO;
import eu.factorx.awac.dto.awac.shared.SublistDTOList;
import eu.factorx.awac.models.code.CodeList;
import eu.factorx.awac.models.code.conversion.CodesEquivalence;
import eu.factorx.awac.service.CodesEquivalenceService;
import eu.factorx.awac.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import play.db.jpa.Transactional;
import play.mvc.Result;
import play.mvc.Security;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@org.springframework.stereotype.Controller
public class TranslationAdminController extends AbstractController {


	@Autowired
	private QuestionService questionService;

	@Autowired
	private CodesEquivalenceService codesEquivalenceService;

	@Autowired
	private ConversionService conversionService;

	@Transactional(readOnly = true)
	@Security.Authenticated(SecuredController.class)
	public Result loadSublists() {
		return ok(new SublistDTOList(getSubListDTOs(), getCodeListDTOs(CodeList.ActivitySource, CodeList.ActivityType)));
	}

	private List<SubListDTO> getSubListDTOs() {
		List<CodesEquivalence> sublistsData = codesEquivalenceService.findAllSublistsData();
		Map<CodeList, SubListDTO> sublistsMaps = new LinkedHashMap<>();
		for (CodesEquivalence codesEquivalence : sublistsData) {
			CodeList codeList = codesEquivalence.getCodeList();
			CodeList referencedCodeList = codesEquivalence.getReferencedCodeList();
			String codeKey = codesEquivalence.getCodeKey();
			if (sublistsMaps.containsKey(codeList)) {
				SubListDTO subListDTO = sublistsMaps.get(codeList);
				if (!subListDTO.getReferencedCodeList().equals(referencedCodeList.name())) {
					throw new RuntimeException("The code list '" + codeList + "' cannot be at the same time a sublist of '" + referencedCodeList + "' and a sublist of '" + subListDTO.getReferencedCodeList() + "'");
				}
				subListDTO.getKeys().add(codeKey);
			} else {
				SubListDTO subListDTO = new SubListDTO();
				subListDTO.setCodeList(codeList.name());
				subListDTO.setReferencedCodeList(referencedCodeList.name());
				subListDTO.getKeys().add(codeKey);
				sublistsMaps.put(codeList, subListDTO);
			}
		}

		return new ArrayList<>(sublistsMaps.values());
	}

}
