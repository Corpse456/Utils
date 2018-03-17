package fileOperation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;

public class GZIPCreator {

    private final int bufferSize = 1024;
    
    public void gzipIt(String fileName, String newFolder) {
        String newPath = newFolder + new File(fileName).getName();
        gzipIt(newPath);
    }

    public void gzipIt(String fileName) {
        byte[] buffer = new byte[bufferSize];
        String newName = fileName + ".gz";
        
        try {
            try (FileInputStream input = new FileInputStream(fileName);
                    GZIPOutputStream gzout = new GZIPOutputStream(new FileOutputStream(newName))) {
                int length;
                while ((length = input.read(buffer)) > 0) {
                    gzout.write(buffer, 0, length);
                }
                gzout.finish();
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        new GZIPCreator().gzipIt("C:/words.txt");
    }
}
