package eu.factorx.awac.controllers;

import eu.factorx.awac.models.data.answer.type.DocumentAnswerValue;
import eu.factorx.awac.models.data.file.StoredFile;
import eu.factorx.awac.service.StoredFileService;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.springframework.beans.factory.annotation.Autowired;
import play.Logger;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;

import java.io.*;
import java.util.Enumeration;

import java.util.Enumeration;
import java.util.List;
import java.util.Random;

import play.mvc.Http.MultipartFormData;


import org.apache.commons.fileupload.servlet.ServletFileUpload;

@org.springframework.stereotype.Controller
public class FilesController extends Controller {

    @Autowired
    private SecuredController securedController;

    @Autowired
    private StoredFileService storedFileService;

    private static final String caracters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";

    // authenticate action cf routes
    @Transactional(readOnly = false)
    public Result upload() {

        Logger.info("upload");


        MultipartFormData body = request().body().asMultipartFormData();
        List<MultipartFormData.FilePart> files = body.getFiles();
        for (MultipartFormData.FilePart filePart : files) {
            String fileName = filePart.getFilename();
            String contentType = filePart.getContentType();
            File file = filePart.getFile();


            String storageKey = generateRandomKey(100);

            try {

                //store
                //TODO implement for heroku
                InputStream is = new FileInputStream(file);

                OutputStream os = new FileOutputStream("/home/florian/temp/" + storageKey);

                byte[] buffer = new byte[1024];
                int bytesRead;
                //read from is to buffer
                while ((bytesRead = is.read(buffer)) != -1) {
                    os.write(buffer, 0, bytesRead);
                }
                is.close();
                //flush OutputStream to write any buffered data to file
                os.flush();
                os.close();


                //create the entity
                StoredFile storedFile = new StoredFile(file.getName(), storageKey, 0, securedController.getCurrentUser());

                storedFileService.saveOrUpdate(storedFile);

                Logger.info("storedFile : " + storedFile);

                //TODO add documentAnswerValue to the DB

            } catch (Exception e) {
                throw new RuntimeException(e);
            }


        } /*else {
            flash("error", "Missing file");
            return redirect(routes.Application.index());
        }*/
        return ok("File uploaded");
    }



	/*
     * ----------------------------------------------------
	 * ----------------- PRIVATE FUNCTIONS -----------------
	 * ----------------------------------------------------
	 */

    private static String generateRandomKey(final int nbCaracters) {

        String result = "";

        final Random rand = new Random();
        for (int i = 0; i < nbCaracters; i++) {
            result += caracters.charAt(rand.nextInt(caracters.length()));
        }

        return result;
    }

}
