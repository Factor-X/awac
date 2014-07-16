package eu.factorx.awac.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

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
}
