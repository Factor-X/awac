package eu.factorx.awac.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import eu.factorx.awac.dto.awac.post.FormProgressDTO;
import eu.factorx.awac.models.data.FormProgress;

@Component
public class FormProgressToFormProgressDTOConverter implements Converter<FormProgress, FormProgressDTO> {
	@Override
	public FormProgressDTO convert(FormProgress source) {

		return new FormProgressDTO(source.getPeriod().getPeriodCode().getKey(), source.getScope().getId(), source.getForm().getIdentifier(), source.getPercentage());


	}
}
