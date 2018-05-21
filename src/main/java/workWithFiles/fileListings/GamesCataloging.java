package workWithFiles.fileListings;

import java.io.File;
import java.util.Collection;
import java.util.Map;

import htmlConnector.HtmlExecutor;
import parsers.CP1251toUTF8;
import workWithFiles.fileAttributes.FolderProperties;
import workWithFiles.fileIO.WriterToFile;

public class GamesCataloging {

    private String DISC_LETTER;
    private String PATH;

    /**
     * @param disc - буква диска
     * @param path - путь для фала с именами
     */
    public GamesCataloging(String disc, String path) {
        DISC_LETTER = disc;
        PATH = path;
    }
    
    public GamesCataloging () {
    }

    /**
     * @return
     */
    public boolean createList() {
        WriterToFile writer = null;
        try {
            writer = new WriterToFile(PATH);
            File disc = new File(DISC_LETTER);
            writer.write(discAtributes(disc));
            for (File f : disc.listFiles()) {
                if (isGame(f)) {
                    long size = new FolderProperties().calculateSize(f);
                    String name = goodNameApplier(f);
                    writer.writeLine(name + "," + size);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            writer.close();
        }
        return true;
    }

    /**
     * @param f - имя папки, для которой необходимо найти имя
     * @return 
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
        changedName = changedName.toLowerCase().replaceAll("repack", "").replaceAll("codex", "").replaceAll("unigamers", "").replaceAll("r.g.", "");
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
                
                String newNameAndPath = null;
                if (!name.equals(newName)) {
                    newNameAndPath = f.getAbsolutePath().replace(name, newName);
                    System.out.println(newNameAndPath);
                    f.renameTo(new File(newNameAndPath));
                    return newName;
                }
                break;
            }
        }
        return name;
    }

    /**
     * @param disc - имя диска, для которого требуется распечатать атрибуты
     * @return
     */
    public String discAtributes(File disc) {
        String string = disc.getPath() + "\r\n";
        string += disc.getFreeSpace() + "\r\n";
        string += disc.getTotalSpace() + "\r\n";
        return string;
    }


    private boolean isGame(File f) {
        if ("$RECYCLE.BIN".equals(f.getName()) || "System Volume Information".equals(f.getName())) return false;
        return true;
    }

    public static void main(String[] args) {
        GamesCataloging catalogs = new GamesCataloging("g:", "D:/Games.exe/Three.csv");
        if (catalogs.createList()) System.out.println("Done");
    }
}
