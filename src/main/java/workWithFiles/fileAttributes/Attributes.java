package workWithFiles.fileAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.DosFileAttributes;

public class Attributes {
    
    public void printAttributes (String path) {
        Path file = Paths.get(path);
        try {
            DosFileAttributes attr = Files.readAttributes(file, DosFileAttributes.class);
            System.out.println("isReadOnly is " + attr.isReadOnly());
            System.out.println("isHidden is " + attr.isHidden());
            System.out.println("isArchive is " + attr.isArchive());
            System.out.println("isSystem is " + attr.isSystem());
        } catch (IOException x) {
            System.err.println("DOS file attributes not supported:" + x);
        }
    }
    
    public static void main (String[] args) {
        new Attributes().printAttributes("d:\\Games.exe\\Settlers 2\\Settler2.exe");
    }
}
