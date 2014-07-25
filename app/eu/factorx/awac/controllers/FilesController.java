package eu.factorx.awac.controllers;

import eu.factorx.awac.models.data.file.StoredFile;
import eu.factorx.awac.service.StoredFileService;
import eu.factorx.awac.util.FileUtil;
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

    private static final String LETTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";

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
        for (MultipartFormData.FilePart filePart : files) {


            File file = filePart.getFile();


            //generate the key => test if the key is already used
            String storageKey=null;

            while(storageKey==null || storedFileService.findByStoredName(storageKey)!=null){

                storageKey = generateRandomKey(100);

            }

            //create the entity
            StoredFile storedFile = new StoredFile(file.getName(), storageKey, 0, securedController.getCurrentUser());

            storedFileService.saveOrUpdate(storedFile);

            //save the file
            FileUtil.save(file, storageKey);

            Logger.info("storedFile : " + storedFile);


        }
        //TODO retrun the storedFileData
        return ok("File uploaded");
    }

    /*
      download a file by is storedFileId
     */
    @Transactional(readOnly = true)
    @Security.Authenticated(SecuredController.class)
    public Result download(long storedFileId) {

        //get the storedFile
        StoredFile storedFile = storedFileService.findById(storedFileId);

        //create an inputStream
        InputStream inputStream = FileUtil.getFileInputStream(storedFile.getStoredName());

        //launch the download
        response().setContentType("application/x-download");
        response().setHeader("Content-disposition","attachment; filename="+storedFile.getOriginalName());
        return ok(inputStream);
    }



	/*
     * ----------------------------------------------------
	 * ----------------- PRIVATE FUNCTIONS -----------------
	 * ----------------------------------------------------
	 */

    private static String generateRandomKey(final int nbLetter) {

        String result = "";

        final Random rand = new Random();
        for (int i = 0; i < nbLetter; i++) {
            result += LETTERS.charAt(rand.nextInt(LETTERS.length()));
        }

        return result;
    }

}
