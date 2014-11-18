package eu.factorx.awac.converter;

import eu.factorx.awac.dto.awac.get.FullCodeLabelDTO;
import eu.factorx.awac.models.code.label.CodeLabel;
import org.springframework.core.convert.converter.Converter;

public class CodeLabelToFullCodeLabelDTOConverter implements Converter<CodeLabel, FullCodeLabelDTO> {

	@Override
	public FullCodeLabelDTO convert(CodeLabel codeLabel) {
		return new FullCodeLabelDTO(codeLabel.getKey(), codeLabel.getLabelEn(), codeLabel.getLabelFr(),
				codeLabel.getLabelNl(), codeLabel.getTopic());
	}

}
