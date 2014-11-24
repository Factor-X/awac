package eu.factorx.awac.service;

import eu.factorx.awac.models.code.WysiwygDocument;
import eu.factorx.awac.models.knowledge.Factor;

public interface WysiwygDocumentService extends PersistenceService<WysiwygDocument> {

    WysiwygDocument findByCategoryAndName(String category, String name);

}
