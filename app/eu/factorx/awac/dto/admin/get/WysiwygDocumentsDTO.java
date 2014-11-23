package eu.factorx.awac.dto.admin.get;

import eu.factorx.awac.dto.DTO;

import java.util.ArrayList;
import java.util.List;

public class WysiwygDocumentsDTO extends DTO {

    private List<WysiwygDocumentDTO> files = new ArrayList<>();

    public WysiwygDocumentsDTO() {
    }

    public WysiwygDocumentsDTO(List<WysiwygDocumentDTO> files) {
        this.files = files;
    }

    public List<WysiwygDocumentDTO> getFiles() {
        return files;
    }

    public void setFiles(List<WysiwygDocumentDTO> files) {
        this.files = files;
    }
}
