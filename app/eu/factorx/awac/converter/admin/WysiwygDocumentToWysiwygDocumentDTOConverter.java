package eu.factorx.awac.converter.admin;

import eu.factorx.awac.dto.admin.get.WysiwygDocumentDTO;
import eu.factorx.awac.models.code.WysiwygDocument;
import org.springframework.core.convert.converter.Converter;

public class WysiwygDocumentToWysiwygDocumentDTOConverter implements Converter<WysiwygDocument, WysiwygDocumentDTO> {

    @Override
    public WysiwygDocumentDTO convert(WysiwygDocument wysiwygDocument) {
        WysiwygDocumentDTO dto = new WysiwygDocumentDTO();

        dto.setName(wysiwygDocument.getName());
        dto.setContent(wysiwygDocument.getContent());
        dto.setCategory(wysiwygDocument.getCategory());

        return dto;
    }
}
