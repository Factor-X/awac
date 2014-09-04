package eu.factorx.awac.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import play.db.jpa.JPA;
import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.CodeList;
import eu.factorx.awac.models.code.conversion.CodesEquivalence;
import eu.factorx.awac.models.code.type.ActivitySourceCode;
import eu.factorx.awac.models.code.type.ActivitySubCategoryCode;
import eu.factorx.awac.models.code.type.ActivityTypeCode;
import eu.factorx.awac.service.CodeConversionService;

@Component
public class CodeConversionServiceImpl extends AbstractJPAPersistenceServiceImpl<CodesEquivalence> implements CodeConversionService {

	private Map<CodeList, List<CodeList>> sublists = null;

	@Override
	public ActivitySourceCode toActivitySourceCode(Code code) {
		String activitySourceCodeKey = getTargetCodeKey(code, CodeList.ActivitySource);
		if (activitySourceCodeKey == null) {
			throw new RuntimeException("Unable to convert " + code + " to an ActivitySourceCode!");
		}
		return new ActivitySourceCode(activitySourceCodeKey);
	}

	@Override
	public ActivityTypeCode toActivityTypeCode(Code code) {
		String activityTypeCodeKey = getTargetCodeKey(code, CodeList.ActivityType);
		if (activityTypeCodeKey == null) {
			throw new RuntimeException("Unable to convert " + code + " to an ActivityTypeCode!");
		}
		return new ActivityTypeCode(activityTypeCodeKey);
	}

	@Override
	public ActivitySubCategoryCode toActivitySubCategoryCode(Code code) {
		String activitySubCategoryCodeKey = getTargetCodeKey(code, CodeList.ActivitySubCategory);
		if (activitySubCategoryCodeKey == null) {
			throw new RuntimeException("Unable to convert " + code + " to an ActivitySubCategoryCode!");
		}
		return new ActivitySubCategoryCode(activitySubCategoryCodeKey);
	}

	@Override
	public List<CodesEquivalence> findAllSublistsData() {
		List<CodesEquivalence> codesEquivalences = JPA.em()
				.createNamedQuery(CodesEquivalence.FIND_ALL_SUBLISTS_DATA, CodesEquivalence.class).getResultList();
		if (sublists == null) {
			sublists = new HashMap<CodeList, List<CodeList>>();
			for (CodesEquivalence codesEquivalence : codesEquivalences) {
				CodeList codeList = codesEquivalence.getCodeList();
				CodeList referencedCodeList = codesEquivalence.getReferencedCodeList();
				if (!sublists.containsKey(referencedCodeList)) {
					ArrayList<CodeList> referencedCodeListSublists = new ArrayList<CodeList>();
					referencedCodeListSublists.add(codeList);
					sublists.put(referencedCodeList, referencedCodeListSublists);
				} else if (!sublists.get(referencedCodeList).contains(codeList)) {
					sublists.get(referencedCodeList).add(codeList);
				}
			}
		}
		return codesEquivalences;
	}

	private String getTargetCodeKey(Code code, CodeList targetCodeList) {
		String targetCodeKey = null;
		if (sublists == null) {
			findAllSublistsData();
		}
		if (sublists.containsKey(targetCodeList) && sublists.get(targetCodeList).contains(code.getCodeList())) {
			// if codeList is a sublist of targetCodeList, targetCodeKey = codeKey => no need to search equivalence
			targetCodeKey = code.getKey();
		} else {
			CodesEquivalence codesEquivalence = findByCodeAndTargetCodeList(code, targetCodeList);
			if (codesEquivalence != null) {
				targetCodeKey = codesEquivalence.getReferencedCodeKey();
			}
		}
		return targetCodeKey;
	}

	private CodesEquivalence findByCodeAndTargetCodeList(Code code, CodeList targetCodeList) {
		List<CodesEquivalence> resultList = JPA.em()
				.createNamedQuery(CodesEquivalence.FIND_BY_CODE_AND_TARGET_CODELIST, CodesEquivalence.class)
				.setParameter("codeList", code.getCodeList())
				.setParameter("codeKey", code.getKey())
				.setParameter("targetCodeList", targetCodeList)
				.getResultList();
		if (resultList.size() > 1) {
			throw new RuntimeException("More than one equivalence found for given parameters (" + code + ", " + targetCodeList + ")");
		}
		if (resultList.size() == 0) {
			return null;
		}
		return resultList.get(0);
	}

	@Override
	public Boolean isSublistOf(CodeList subList, CodeList mainList) {
		if (sublists == null) {
			findAllSublistsData();
		}
		List<CodeList> mainListSublists = sublists.get(mainList);
		return (mainListSublists != null) && mainListSublists.contains(subList);
	}

}
