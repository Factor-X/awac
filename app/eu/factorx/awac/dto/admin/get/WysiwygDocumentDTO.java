package eu.factorx.awac.dto.admin.get;

import eu.factorx.awac.dto.DTO;
import eu.factorx.awac.dto.validation.annotations.NotNull;

public class WysiwygDocumentDTO extends DTO {

    @NotNull
    private String name;

    @NotNull
    private String category;

    @NotNull
    private String content;

    public WysiwygDocumentDTO() {
    }

    public WysiwygDocumentDTO(String name, String category, String content) {
        this.name = name;
        this.category = category;
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}


