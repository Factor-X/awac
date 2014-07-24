package eu.factorx.awac.service.impl;

import eu.factorx.awac.models.business.Scope;
import eu.factorx.awac.models.data.file.StoredFile;
import eu.factorx.awac.service.StoredFileService;
import org.springframework.stereotype.Component;

@Component
public class StoredFileServiceImpl  extends AbstractJPAPersistenceServiceImpl<StoredFile> implements StoredFileService {
}
