package eu.factorx.awac.converter;

import eu.factorx.awac.dto.awac.get.FormProgressListDTO;
import eu.factorx.awac.dto.awac.post.FormProgressDTO;
import eu.factorx.awac.dto.myrmex.get.PersonDTO;
import eu.factorx.awac.models.account.Account;
import eu.factorx.awac.models.data.FormProgress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FormProgressListToFormProgressListDTOConverter implements Converter<List<FormProgress>, FormProgressListDTO> {

	@Autowired
	private FormProgressToFormProgressDTOConverter formProgressToFormProgressDTOConverter;


	@Override
	public FormProgressListDTO convert(List<FormProgress> source) {

		List<FormProgressDTO> formProgressDTOs = new ArrayList<>();

		for (FormProgress formProgress : source) {
			formProgressDTOs.add(formProgressToFormProgressDTOConverter.convert(formProgress));
		}

		return new FormProgressListDTO(formProgressDTOs);
	}
}
