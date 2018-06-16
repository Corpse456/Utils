package workWithFiles.folderIcon;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import workWithFiles.fileIO.ReaderFromFile;
import workWithFiles.fileIO.WriterToFile;

public class IconAssigning {

    private final String desktopIni = "[.ShellClassInfo]\r\nIconResource=???,0\r\n";
    private final String desktopIniName = "desktop.ini";

    public void assingning (String folder) {
        assingning(new File(folder));
    }

    public void assingning (File folder) {
        File[] listFiles = folder.listFiles();

        for (File file : listFiles) {
            System.out.println(file);
            if (!file.isDirectory()) continue;
            
            String fileName = currentFolderFinder(file.getAbsolutePath() + "\\");
            
            String newDesktopIni = file + "/" + desktopIniName;
            if (!fileName.isEmpty()) {
                WriterToFile writer = new WriterToFile(newDesktopIni);
                writer.write(desktopIni.replace("???", fileName));
                writer.close();
            }
            
            Path path = Paths.get(newDesktopIni);
            try {
                Files.setAttribute(path, "dos:hidden", true);
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    private String currentFolderFinder (String path) {
        File currentDeskTopIni = new File(path + desktopIniName);
        if (currentDeskTopIni.exists()) {
            ReaderFromFile reader = new ReaderFromFile(currentDeskTopIni);
            String content = reader.readAll();
            reader.close();
            
            if (content.contains(":")) content = content.replaceAll("D:", "d:").replace(path, "");
            else return "";

            WriterToFile writer = new WriterToFile(currentDeskTopIni);
            writer.write(content);
            writer.close();
            return "";
        }
        
        List<File> collect = new ArrayList<>();
        try {
            collect = Files.walk(Paths.get(path))
                    .filter(f -> f.toFile().getName().contains(".ico") || f.toFile().getName().contains(".exe"))
                    .map(Path::toFile)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        
        for (File current : collect) {
            if (current.getName().contains(".ico")) return current.getAbsolutePath().replace(path, "");
        }
        
        for (File current : collect) {
            if (current.getName().contains(".exe") && !current.getName().contains("unins")) {
                return current.getAbsolutePath().replace(path, "");
            }
        }
        
        return "";
    }

    public static void main (String[] args) {
        new IconAssigning().assingning("d:\\Games.exe\\");
        System.out.println("Done");
    }
}
