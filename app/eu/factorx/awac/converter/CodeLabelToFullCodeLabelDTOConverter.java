package eu.factorx.awac.converter;

import eu.factorx.awac.dto.awac.get.UpdateCodeLabelDTO;
import eu.factorx.awac.models.code.label.CodeLabel;
import org.springframework.core.convert.converter.Converter;

public class CodeLabelToFullCodeLabelDTOConverter implements Converter<CodeLabel, UpdateCodeLabelDTO> {

	@Override
	public UpdateCodeLabelDTO convert(CodeLabel codeLabel) {
		return new UpdateCodeLabelDTO(codeLabel.getId(), codeLabel.getKey(), codeLabel.getLabelEn(), codeLabel.getLabelFr(),
				codeLabel.getLabelNl(), codeLabel.getTopic(), codeLabel.getOrderIndex());
	}

}
