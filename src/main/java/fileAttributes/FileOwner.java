package fileAttributes;

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
            }
        }
    }

    private String getOwner (File file) {
        return getOwner(file.getAbsolutePath());
    }

    private String getOwner (String stringPath) {
        Path path = Paths.get(stringPath);
        FileOwnerAttributeView ownerAttributeView = Files.getFileAttributeView(path, FileOwnerAttributeView.class);

        UserPrincipal owner = null;
        try {
            owner = ownerAttributeView.getOwner();
        } catch (IOException e) {
            System.out.println(stringPath);
        }
        return owner.getName();
    }
    
    public static void main (String[] args) {
        FileOwner fileOwner = new FileOwner();
        List<File> files = fileOwner.getListFilesOfCurrentOwner("z:\\ASU\\Common\\", "MINSK\\Neznaev_AI");
        for (File file : files) {
            System.out.println(file.getAbsolutePath());
        }
    }
}
