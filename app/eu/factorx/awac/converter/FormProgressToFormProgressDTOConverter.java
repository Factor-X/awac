package eu.factorx.awac.converter;

import eu.factorx.awac.dto.awac.get.FormProgressListDTO;
import eu.factorx.awac.dto.awac.post.FormProgressDTO;
import eu.factorx.awac.models.data.FormProgress;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FormProgressToFormProgressDTOConverter implements Converter<FormProgress, FormProgressDTO> {
	@Override
	public FormProgressDTO convert(FormProgress source) {

		return new FormProgressDTO(source.getPeriod().getId(), source.getScope().getId(), source.getForm().getIdentifier(), source.getPercentage());


	}
}
