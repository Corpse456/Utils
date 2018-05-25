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
    private List<Owner> owners;

    public String[] getListFilesOfCurrentOwner (String path, String owner) {
        this.owner = owner;
        files = new ArrayList<>();
        File[] content = new File(path).listFiles();

        scanFilesInFolder(content);

        return files.toArray(new String[files.size()]);
    }

    private void scanFilesInFolder (File[] listFiles) {
        for (File file : listFiles) {
            if (file.isDirectory() && file.listFiles() != null) scanFilesInFolder(file.listFiles());

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

    public List<Owner> findAllOwners (String path) {
        owners = new ArrayList<>();
        File[] content = new File(path).listFiles();

        findAll(content);

        return owners;
    }

    private void findAll (File[] listFiles) {
        for (File file : listFiles) {
            if (file.isDirectory() && file.listFiles() != null) findAll(file.listFiles());

            String ownerName = getOwner(file);
            String[] nameAndDomain = ownerName.split("\\\\");
            if (nameAndDomain.length < 2) continue;

            ownerName = nameAndDomain[1];

            Owner current = new Owner(ownerName, 1, file.length());
            
            if (owners.contains(current)) {
                owners.forEach(a -> {
                    if (a.getOwner().equals(current.getOwner())) {
                        a.setAmount(a.getAmount() + 1);
                        a.setSize(a.getSize() + current.getSize());
                    }
                });
            } else owners.add(current);
        }
    }

    public static void main (String[] args) {
        FileOwner fileOwner = new FileOwner();
        List<Owner> findAllOwners = fileOwner.findAllOwners("z:\\");
        findAllOwners.sort((o1, o2) -> o1.getSize().compareTo(o2.getSize()));
        
        for (Owner owner : findAllOwners) {
            System.out.println(owner);
        }
        // fileOwner.getListFilesOfCurrentOwner("z:\\ASU\\Common\\205\\", "Neznaev_AI");

        System.out.println("Done");
    }
}
