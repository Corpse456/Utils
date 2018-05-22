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
    private List<String> files;

    public String[] getListFilesOfCurrentOwner (String path, String owner) {
        this.owner = owner;
        files = new ArrayList<>();
        File[] content = new File(path).listFiles();

        scanFilesInFolder(content);
        
        return files.toArray(new String[files.size()]);
    }
    
    private void scanFilesInFolder (File[] listFiles) {
        /*Files.walk(Paths.get("ваш каталог тут"))
        .filter(Files::isRegularFile)
        .map(Path::toFile)
        .collect(Collectors.toList())
   Или если вам нужны только пути, то просто

   Files.walk(Paths.get("ваш каталог тут"))
        .filter(Files::isRegularFile)
        .collect(Collectors.toList())*/
        
        for (File file : listFiles) {
            if (file.isDirectory() && file.listFiles() != null) scanFilesInFolder(file.listFiles());
            else {
                String ownerName = getOwner(file);
                String[] nameAndDomain = ownerName.split("\\\\");
                if (nameAndDomain.length < 2) continue;
                
                ownerName = nameAndDomain[1];
                
                if (owner.toLowerCase().equals(ownerName.toLowerCase())) {
                    files.add(file.getAbsolutePath());
                    System.out.println(file.getAbsolutePath());
                }
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
        System.out.println(fileOwner.getOwner("d:\\Neznaev\\Maven.bat"));
        fileOwner.getListFilesOfCurrentOwner("z:\\ASU\\Common\\205\\", "Neznaev_AI");
        
        System.out.println("Done");
    }
}
