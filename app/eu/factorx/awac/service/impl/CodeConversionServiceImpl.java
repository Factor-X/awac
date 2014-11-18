package eu.factorx.awac.service.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import play.Logger;
import play.db.jpa.JPA;
import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.code.CodeList;
import eu.factorx.awac.models.code.conversion.CodeConversion;
import eu.factorx.awac.models.code.conversion.CodesEquivalence;
import eu.factorx.awac.models.code.conversion.ConversionCriterion;
import eu.factorx.awac.models.code.type.ActivitySourceCode;
import eu.factorx.awac.models.code.type.ActivitySubCategoryCode;
import eu.factorx.awac.models.code.type.ActivityTypeCode;
import eu.factorx.awac.service.CodeConversionService;
import eu.factorx.awac.util.MyrmexRuntimeException;

@Component
public class CodeConversionServiceImpl extends AbstractJPAPersistenceServiceImpl<CodeConversion> implements CodeConversionService {

	@Override
	public ActivitySourceCode toActivitySourceCode(Code code) {
		CodesEquivalence codesEquivalence = findByCodeAndTargetCodeList(code, CodeList.ActivitySource);
		if (codesEquivalence == null) {
			throw new RuntimeException("Unable to convert " + code + " to an ActivitySourceCode!");
		}
		return new ActivitySourceCode(codesEquivalence.getReferencedCodeKey());
	}

	@Override
	public ActivityTypeCode toActivityTypeCode(Code code) {
		CodesEquivalence codesEquivalence = findByCodeAndTargetCodeList(code, CodeList.ActivityType);
		if (codesEquivalence == null) {
			throw new RuntimeException("Unable to convert " + code + " to an ActivityTypeCode!");
		}
		return new ActivityTypeCode(codesEquivalence.getReferencedCodeKey());
	}

	@Override
	public ActivitySubCategoryCode toActivitySubCategoryCode(Code code) {
		CodesEquivalence codesEquivalence = findByCodeAndTargetCodeList(code, CodeList.ActivitySubCategory);
		if (codesEquivalence == null) {
			throw new RuntimeException("Unable to convert " + code + " to an ActivitySubCategoryCode!");
		}
		return new ActivitySubCategoryCode(codesEquivalence.getReferencedCodeKey());
	}

	@Override
	public Boolean isSublistOf(CodeList subList, CodeList mainList) {
		Long nbCodesEquivalences = JPA.em()
			.createNamedQuery(CodesEquivalence.COUNT_SUBLIST_EQUIVALENCES, Long.class)
			.setParameter(CodesEquivalence.CODE_LIST_PROPERTY_NAME, subList)
			.setParameter(CodesEquivalence.REFERENCED_CODE_LIST_PROPERTY_NAME, mainList)
			.getSingleResult();
		return (nbCodesEquivalences > 0);
	}

    @Override
    public <T extends Code> T getConversionCode(T code, ConversionCriterion conversionCriterion){

        List<CodeConversion> resultList = JPA.em().createNamedQuery(CodeConversion.FIND_BY_CODE_LIST_AND_KEY_AND_CRITERION, CodeConversion.class)
                .setParameter("codeList", code.getCodeList()).setParameter("codeKey", code.getKey()).setParameter("conversionCriterion",conversionCriterion).getResultList();


        if(resultList.size()==0){
            return null;
        }
        else if(resultList.size()>1){
            throw new RuntimeException("More than one equivalence found for given parameters ("+code.getCodeList()+","+ code + ", " + conversionCriterion + ")");
        }
        String codeKey = resultList.get(0).getReferencedCodeKey();

        //find the code
        try {
            return (T) code.getClass().getConstructor(String.class).newInstance(codeKey);
        } catch (Exception e) {
            throw new MyrmexRuntimeException("cannot create the code "+ code.getClass().getName()+" with parameter "+code+", "+conversionCriterion);
        }
    }

	@Override
	public void removeAll() {
		int nbDeleted = JPA.em().createNamedQuery(CodeConversion.REMOVE_ALL).executeUpdate();
		Logger.info("Deleted {} CodeConversion entities", nbDeleted);
	}

	private CodesEquivalence findByCodeAndTargetCodeList(Code code, CodeList targetCodeList) {
		List<CodesEquivalence> resultList = JPA.em()
				.createNamedQuery(CodesEquivalence.FIND_BY_CODE_AND_TARGET_CODELIST, CodesEquivalence.class)
				.setParameter(CodesEquivalence.CODE_LIST_PROPERTY_NAME, code.getCodeList())
				.setParameter(CodesEquivalence.CODE_KEY_PROPERTY_NAME, code.getKey())
				.setParameter(CodesEquivalence.REFERENCED_CODE_LIST_PROPERTY_NAME, targetCodeList)
				.getResultList();

		if (resultList.size() > 1) {
			throw new RuntimeException("More than one equivalence found for given parameters (" + code + ", " + targetCodeList + ")");
		}
		if (resultList.size() == 0) {
			return null;
		}
		return resultList.get(0);
	}

}
