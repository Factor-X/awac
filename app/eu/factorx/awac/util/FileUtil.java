package eu.factorx.awac.util;

import com.amazonaws.services.s3.model.*;
import eu.factorx.awac.plugins.S3Plugin;
import play.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by gaetan on 7/7/14.
 */
public class FileUtil {


    public static String getContents(String path) throws IOException {
        File file = new File(path);
        FileInputStream fis = new FileInputStream(file);
        byte[] data = new byte[(int) file.length()];
        fis.read(data);
        fis.close();
        String s = new String(data, "UTF-8");
        return s;
    }

    public static void save(File file, String name) {
        save(file, name, false);
    }

    public static void save(File file, String name, boolean isPublic) {
        if (S3Plugin.amazonS3 == null) {
            Logger.error("Could not save because amazonS3 was null");
            throw new RuntimeException("Could not save");
        } else {
            String bucket = S3Plugin.s3Bucket;

            PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, name, file);
            if (isPublic) {
                putObjectRequest.withCannedAcl(CannedAccessControlList.PublicRead);
            }
            S3Plugin.amazonS3.putObject(putObjectRequest); // upload file
        }
    }

    public static  InputStream getFileInputStream(String storedName) {

        if (S3Plugin.amazonS3 == null) {
            Logger.error("Could not save because amazonS3 was null");
            throw new RuntimeException("Could not save");
        } else {
            GetObjectRequest getObjectRequest = new GetObjectRequest(S3Plugin.s3Bucket, storedName);

            S3Object s3Object = S3Plugin.amazonS3.getObject(getObjectRequest);

            return s3Object.getObjectContent();
        }
    }

}
