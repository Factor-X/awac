package eu.factorx.awac.service;

import eu.factorx.awac.models.data.file.StoredFile;

public interface StoredFileService  extends PersistenceService<StoredFile> {

    public StoredFile findByStoredName(String storedName);
}
