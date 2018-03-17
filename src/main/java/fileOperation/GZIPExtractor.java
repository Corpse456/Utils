package fileOperation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;

public class GZIPExtractor {

    private final int bufferSize = 1024;

    public void unGZIP(String fileName, String newFolder) {
        String newPath = newFolder + new File(fileName).getName();
        unGZIP(newPath);
    }
    
    public void unGZIP(String fileName) {
        byte[] buffer = new byte[bufferSize];
        String newName = fileName.substring(0, fileName.lastIndexOf("."));
        
        try (GZIPInputStream gzipInput = new GZIPInputStream(new FileInputStream(fileName));
                FileOutputStream output = new FileOutputStream(newName)) {
            int length = 0;
            while ((length = gzipInput.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
