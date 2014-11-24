package eu.factorx.awac.models.code;

import eu.factorx.awac.models.AuditedAbstractEntity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "wysiwyg_document", uniqueConstraints = {@UniqueConstraint(columnNames = {WysiwygDocument.COLUMN_NAME_CATEGORY, WysiwygDocument.COLUMN_NAME_NAME})})
public class WysiwygDocument extends AuditedAbstractEntity implements Serializable {

    public static final String COLUMN_NAME_NAME     = "name";
    public static final String COLUMN_NAME_CONTENT  = "content";
    public static final String COLUMN_NAME_CATEGORY = "category";

    @Column(name = COLUMN_NAME_NAME, nullable = false, length = 255)
    protected String name;

    @Column(name = COLUMN_NAME_CONTENT, nullable = false, columnDefinition = "TEXT")
    protected String content;

    @Column(name = COLUMN_NAME_CATEGORY, nullable = false, length = 255)
    protected String category;

    public WysiwygDocument() {
        super();
    }

    public WysiwygDocument(String name, String category, String content) {
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
