package eu.factorx.awac.service.impl;

import eu.factorx.awac.models.code.WysiwygDocument;
import eu.factorx.awac.service.WysiwygDocumentService;
import org.springframework.stereotype.Component;
import play.db.jpa.JPA;

@Component
public class WysiwygDocumentServiceImpl extends AbstractJPAPersistenceServiceImpl<WysiwygDocument> implements WysiwygDocumentService {
    @Override
    public WysiwygDocument findByCategoryAndName(String category, String name) {
        return JPA.em().createQuery("" +
            "select e from WysiwygDocument e " +
            "where e.category = :category " +
            "and e.name = :name", WysiwygDocument.class)
            .setParameter("category", category)
            .setParameter("name", name)
            .getSingleResult();
    }
}
