package workWithFiles.fileAttributes.fileOwnerDeleter.finder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileOwnerAttributeView;
import java.nio.file.attribute.UserPrincipal;

import workWithFiles.fileAttributes.fileOwnerDeleter.gui.Result;

public class FileOwner {

    private String owner;
    private MyModel content;
    private Result result;
    private boolean stop;

    public FileOwner (MyModel content2, Result result) {
        this.content = content2;
        this.result = result;
    }

    public boolean addListFilesOfCurrentOwnertoModel (String path, String owner) {
        this.owner = owner;
        stop = false;

        scanFilesInFolder(new File(path).listFiles());
        return !stop;
    }

    private void scanFilesInFolder (File[] listFiles) {
        for (File file : listFiles) {

            if (stop) return;

            if (file.isDirectory() && file.listFiles() != null) scanFilesInFolder(file.listFiles());

            String ownerName = getOwner(file);
            String[] nameAndDomain = ownerName.split("\\\\");
            if (nameAndDomain.length < 2) continue;

            ownerName = nameAndDomain[1];

            if (owner.toLowerCase().equals(ownerName.toLowerCase())) {
                content.add(file);
                result.repackWindow();
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
            return owner.getName();
        } catch (IOException e) {
            System.err.println(stringPath);
        }

        return "";
    }

    public void stop () {
        stop = true;
    }
}
