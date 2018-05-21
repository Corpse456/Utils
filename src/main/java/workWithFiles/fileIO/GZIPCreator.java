package workWithFiles.fileIO;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.zip.GZIPOutputStream;

public class GZIPCreator {

    private final int bufferSize = 1024;
    private String newName = "";
    
    public void gzipIt(URL fileName, String newFolder) {
        gzipIt(fileName.getPath(), newFolder);
    }
    
    public void gzipIt(String fileName, String newFolder) {
        newName = newFolder + new File(fileName).getName();
        gzipIt(fileName);
    }

    public void gzipIt(URL fileName) {
        gzipIt(fileName.getPath());
    }
    
    public void gzipIt(String fileName) {
        byte[] buffer = new byte[bufferSize];
        if (newName.isEmpty()) newName = fileName + ".gz";
        else newName += ".gz";
        
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
        new GZIPCreator().gzipIt(GZIPCreator.class.getClassLoader().getResource("BelarusCities.csv"), "C:/");
        System.out.println("Done");
    }
}
