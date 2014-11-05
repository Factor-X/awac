package eu.factorx.awac.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import eu.factorx.awac.dto.awac.shared.BaseIndicatorDTO;
import eu.factorx.awac.models.code.Code;
import eu.factorx.awac.models.knowledge.BaseIndicator;

@Component
public class BaseIndicatorToBaseIndicatorDTOConverter implements Converter<BaseIndicator, BaseIndicatorDTO> {

	@Override
	public BaseIndicatorDTO convert(BaseIndicator baseIndicator) {
		BaseIndicatorDTO dto = new BaseIndicatorDTO();
		dto.setKey(getCodeKey(baseIndicator.getCode()));
		dto.setIsoScopeKey(getCodeKey(baseIndicator.getIsoScope()));
		dto.setIndicatorCategoryKey(getCodeKey(baseIndicator.getIndicatorCategory()));
		dto.setActivityCategoryKey(getCodeKey(baseIndicator.getActivityCategory()));
		dto.setActivitySubCategoryKey(getCodeKey(baseIndicator.getActivitySubCategory()));
		dto.setActivityOwnership(baseIndicator.getActivityOwnership());
		return dto;
	}

	private static String getCodeKey(Code code) {
		if (code == null) {
			return null;
		}
		return code.getKey();
	}

}
