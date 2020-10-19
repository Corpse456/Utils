package workWithFiles.folderIcon;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.junit.Assert;
import workWithFiles.fileIO.ReaderFromFile;
import workWithFiles.fileIO.WriterToFile;

public class IconAssigning {

    private static final List<String> IGNORE_LIST = Arrays.asList("cleanup.exe", "touchup.exe", "vcredist_x64.exe", "vcredist_x86.exe", "vc_redist.x64.exe", "vc_redist.x86.exe", "activationui.exe", "gog.ico", "7za.exe", "dxsetup.exe", "vcredist1_x64.exe", "directx web setup.exe", "config.ico", "editor.ico", "config.exe", "editor.exe", "configure.exe", "osi.exe", "berkelium.exe", "crashreportclient.exe", "ppmrender.exe", "wow_helper.exe", "mapeditor.exe", "ubisoftgamelauncher.exe", "oalinst.exe", "uplay.exe", "uplaybrowser.exe", "dxwebsetup.exe");
    private static final String INI_CONTENT = "[.ShellClassInfo]\r\nIconResource=???,0\r\n";
    private static final String DESKTOP_INI = "desktop.ini";

    public void assigning(String folder) {
        assigning(new File(folder));
    }

    public void assigning(File folder) {
        File[] listFiles = folder.listFiles();
        Assert.assertNotNull("No files in folder: " + folder, listFiles);

        for (File file : listFiles) {
            if (!file.isDirectory()) {
                continue;
            }

            String fileName = currentFolderFinder(file.getAbsolutePath() + "\\");

            String newDesktopIni = file + "/" + DESKTOP_INI;
            if (!fileName.isEmpty()) {
                WriterToFile writer = new WriterToFile(newDesktopIni);
                writer.write(INI_CONTENT.replace("???", fileName));
                writer.close();
            }

            Path path = Paths.get(newDesktopIni);
            try {
                Files.setAttribute(path, "dos:hidden", true);
                Files.setAttribute(Paths.get(file.getAbsolutePath()), "dos:readonly", true);
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
            System.out.println(file);
        }
    }

    private String currentFolderFinder(String path) {
        File currentDeskTopIni = new File(path + DESKTOP_INI);
        if (currentDeskTopIni.exists()) {
            ReaderFromFile reader = new ReaderFromFile(currentDeskTopIni);
            String content = reader.readAll();
            reader.close();

            if (content.contains(":")) {
                content = content.replaceAll("D:", "d:").replace(path, "");
            } else {
                return "";
            }

            WriterToFile writer = new WriterToFile(currentDeskTopIni);
            writer.write(content);
            writer.close();
            return "";
        }

        List<File> collect = new ArrayList<>();
        try {
            collect = Files.walk(Paths.get(path))
                .filter(isIcoOrExe())
                .map(Path::toFile)
                .sorted(getComparator())
                .collect(Collectors.toList());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        for (File current : collect) {
            if (!current.getName().contains("unins")) {
                return current.getAbsolutePath().replace(path, "");
            }
        }

        return "";
    }

    private Comparator<File> getComparator() {
        final Comparator<File> comparing = Comparator.comparing(f -> {
            final String[] split = f.getName().split("\\.");
            return split[split.length - 1];
        });
        return comparing.reversed();
    }

    private Predicate<Path> isIcoOrExe() {
        return f -> {
            final String name = f.toFile().getName().toLowerCase();
            return (name.endsWith(".ico") || name.endsWith(".exe")) && !IGNORE_LIST.contains(name.toLowerCase());
        };
    }

    public static void main(String[] args) {
        new IconAssigning().assigning("d:\\Games.exe\\");
        System.out.println("Done");
    }
}
