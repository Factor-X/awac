package eu.factorx.awac.controllers;

import eu.factorx.awac.dto.awac.post.FilesUploadedDTO;
import eu.factorx.awac.models.data.file.StoredFile;
import eu.factorx.awac.service.StoredFileService;
import eu.factorx.awac.util.FileUtil;
import eu.factorx.awac.util.KeyGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import play.Logger;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;

import java.io.*;

import java.util.List;
import java.util.Random;

import play.mvc.Http.MultipartFormData;
import play.mvc.Security;

@org.springframework.stereotype.Controller
public class FilesController extends Controller {

    @Autowired
    private SecuredController securedController;

    @Autowired
    private StoredFileService storedFileService;

    /**
     *
     * @return
     */
    @Transactional(readOnly = false)
    @Security.Authenticated(SecuredController.class)
    public Result upload() {

        Logger.info("upload");


        MultipartFormData body = request().body().asMultipartFormData();
        List<MultipartFormData.FilePart> files = body.getFiles();

        Logger.info("files : "+files.size());


        FilesUploadedDTO filesUploadedDTO = null;

        if(files!=null) {

            File file = files.get(0).getFile();
            String fileName = files.get(0).getFilename();

            //generate the key => test if the key is already used
            String storageKey=null;

            while(storageKey==null || storedFileService.findByStoredName(storageKey)!=null){
                storageKey = KeyGenerator.generateRandomKey(100);
            }

            //create the entity
            StoredFile storedFile = new StoredFile(fileName, storageKey, 0, securedController.getCurrentUser());

            //and save
            storedFileService.saveOrUpdate(storedFile);

            //save the file
            //TODO FileUtil.save(file, storageKey);

            //complete the result
            filesUploadedDTO = new FilesUploadedDTO(storedFile.getId(),storedFile.getOriginalName());

            Logger.info("storedFile : " + storedFile);
        }
        return ok(filesUploadedDTO);
    }

    /*
      download a file by is storedFileId
     */
    @Transactional(readOnly = true)
    @Security.Authenticated(SecuredController.class)
    public Result download(long storedFileId) {

        //get the storedFile
        StoredFile storedFile = storedFileService.findById(storedFileId);

        if(storedFile==null){
            throw new RuntimeException("File "+storedFileId+" was not found");
        }

        //create an inputStream
        InputStream inputStream = FileUtil.getFileInputStream(storedFile.getStoredName());

        //launch the download

/*
        response().setHeader(	"Content-Length",
                "23000");
*/
        response().setContentType("application/octet-stream");
        response().setHeader("Content-disposition","attachment; filename="+storedFile.getOriginalName());

        Status ok = ok(inputStream);

        return ok;
    }

}
