package eu.factorx.awac.service.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import play.Logger;
import play.db.jpa.JPA;
import eu.factorx.awac.models.data.file.StoredFile;
import eu.factorx.awac.service.StoredFileService;

@Component
public class StoredFileServiceImpl  extends AbstractJPAPersistenceServiceImpl<StoredFile> implements StoredFileService {

    @Override
    public StoredFile findByStoredName(String storedName) {

        List<StoredFile> resultList = JPA.em().createNamedQuery(StoredFile.FIND_BY_STORED_NAME, StoredFile.class)
                .setParameter("storedName", storedName).getResultList();

        if (resultList.size() > 1) {
            String errorMsg = "More than one question with code = '" + storedName + "'";
            Logger.error(errorMsg);
            throw new RuntimeException(errorMsg);
        }
        if (resultList.size() == 0) {
            return null;
        }
        return resultList.get(0);
    }
}
