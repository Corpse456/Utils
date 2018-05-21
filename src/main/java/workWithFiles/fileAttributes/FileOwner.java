package workWithFiles.fileAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileOwnerAttributeView;
import java.nio.file.attribute.UserPrincipal;
import java.util.ArrayList;
import java.util.List;

public class FileOwner {
    
    private String owner;
    private List<File> files;

    public List<File> getListFilesOfCurrentOwner (String path, String owner) {
        this.owner = owner;
        files = new ArrayList<>();
        File[] content = new File(path).listFiles();
        
        scanFilesInFolder(content);
        
        return files;
    }
    
    private void scanFilesInFolder (File[] listFiles) {
        for (File file : listFiles) {
            if (file.isDirectory()) scanFilesInFolder(file.listFiles());
            else if (owner.equals(getOwner(file))) {
                files.add(file);
                System.out.println(file.getAbsolutePath());
            }
        }
    }

    public String getOwner (File file) {
        return getOwner(file.getAbsolutePath());
    }

    public String getOwner (String stringPath) {
        Path path = Paths.get(stringPath);
        FileOwnerAttributeView ownerAttributeView = Files.getFileAttributeView(path, FileOwnerAttributeView.class);

        UserPrincipal owner = null;
        try {
            owner = ownerAttributeView.getOwner();
        } catch (IOException e) {
            System.err.println(stringPath);
        }
        return owner.getName();
    }
    
    public static void main (String[] args) {
        FileOwner fileOwner = new FileOwner();
        
        fileOwner.getListFilesOfCurrentOwner("z:\\ASU\\Common\\", "MINSK\\Neznaev_AI");
        
        System.out.println("Done");
    }
}
