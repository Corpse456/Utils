package workWithFiles.fileListings;

import htmlConnector.HtmlExecutor;
import org.apache.commons.lang3.StringUtils;
import parsers.CP1251toUTF8;
import workWithFiles.fileAttributes.FolderProperties;
import workWithFiles.fileIO.WriterToFile;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class GamesCataloging {

    static final String DELIMETER = ",";
    private String DISC_LETTER;
    private String PATH;
    private static final List<String> EXCLUDES = Arrays.asList("$RECYCLE.BIN", "System Volume Information", "My");

    /**
     * @param disc - буква диска
     * @param path - путь для фала с именами
     */
    public GamesCataloging(String disc, String path) {
        DISC_LETTER = disc + ":";
        PATH = path;
    }

    public GamesCataloging() {
    }

    /**
     * @return
     */
    public boolean createList() {
        return createList(true);
    }

    /**
     * @param rename - find or not name of the game in google
     * @return
     */
    public boolean createList(boolean rename) {
        WriterToFile writer = null;
        try {
            writer = new WriterToFile(PATH);
            File disc = new File(DISC_LETTER);

            writer.write(discAtributes(disc));

            for (File f : disc.listFiles()) {
                if (!EXCLUDES.contains(f.getName())) {
                    long size = new FolderProperties().calculateSize(f);

                    String name = f.getName();
                    if (rename) {
                        name = goodNameApplier(f);
                    }

                    writer.writeLine(name + DELIMETER + size + DELIMETER + f.lastModified());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
        return true;
    }

    /**
     * @param f - имя папки, для которой необходимо найти имя
     * @return дата выпуска игры
     */
    private String goodNameApplier(File f) {
        String name = f.getName();
        String changedName = name;
        System.out.println(name);

        // if f - is file, remember extension
        String extension = "";
        if (f.isFile()) {
            int lastIndexOf = changedName.lastIndexOf(".");
            if (lastIndexOf > 0) {
                extension = changedName.substring(lastIndexOf, changedName.length());
                changedName = changedName.substring(0, lastIndexOf);
            }
        }

        //replace all in brackets like this - []
        changedName = changedName.replaceAll("\\[.*]", "");
        changedName =
            changedName.toLowerCase().replaceAll("repack", "").replaceAll("codex", "").replaceAll("unigamers", "")
                .replaceAll("r.g.", "");
        changedName = changedName.replaceAll("_", " ");
        System.out.println(changedName);

        HtmlExecutor exec = new HtmlExecutor();
        Map<String, String> googleAnswer = exec.findInGoogle("Википедия " + changedName);

        Collection<String> googleLinks = googleAnswer.values();

        String series = new CP1251toUTF8().convert("серия");
        for (String link : googleLinks) {
            if (link.contains("wikipedia.org/wiki") && !link.contains(series)) {
                String newName = new HtmlExecutor().wikiExecutor(link);
                //replace type like "(game, 2014)" for (2014)
                newName = newName.replace("(игра, ", "(");
                newName = newName.replace(" (игра)", "");
                //replace ":" - invalid character in file name
                newName = newName.replaceAll(":", " -").replaceAll("  ", " ");
                newName += extension;

                String newNameAndPath;
                if (!name.equals(newName)) {
                    newNameAndPath = f.getAbsolutePath().replace(name, newName);
                    System.out.println(newNameAndPath);
                    rename(f, newNameAndPath);
                    return newName;
                }
                break;
            }
        }
        return name;
    }

    private void rename(final File f, final String newNameAndPath) {
        System.err.println(f.getAbsolutePath() + " will be renamed to " + newNameAndPath);
        String input = new Scanner(System.in).nextLine();
        if (StringUtils.isNotEmpty(input) && "y".equals(input.toLowerCase())) {
            f.renameTo(new File(newNameAndPath));
        }
    }

    /**
     * @param disc - имя диска, для которого требуется распечатать атрибуты
     * @return
     */
    public String discAtributes(File disc) {
        String string = disc.getFreeSpace() + "\r\n";
        string += disc.getTotalSpace() + "\r\n";
        return string;
    }

    public static void main(String[] args) {
        GamesCataloging catalogs = new GamesCataloging("x", "D:/Games.exe/Four.csv");
        if (catalogs.createList(false)) {
            System.out.println("Done");
        }
    }
}
