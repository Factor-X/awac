package eu.factorx.awac.converter;

import eu.factorx.awac.dto.awac.shared.CodesEquivalenceDTO;
import eu.factorx.awac.models.code.conversion.CodesEquivalence;
import org.springframework.core.convert.converter.Converter;

public class CodesEquivalenceToCodesEquivalenceDTOConverter implements Converter<CodesEquivalence, CodesEquivalenceDTO> {

	@Override
	public CodesEquivalenceDTO convert(CodesEquivalence codesEquivalence) {
		return new CodesEquivalenceDTO(codesEquivalence.getCodeKey(), codesEquivalence.getReferencedCodeList().toString(), codesEquivalence.getReferencedCodeKey());
	}
}
