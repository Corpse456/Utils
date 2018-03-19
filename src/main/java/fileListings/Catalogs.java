package fileListings;

import java.io.File;
import java.util.Collection;
import java.util.Map;

import fileOperation.WriterToFile;
import htmlConnector.HtmlExecutor;
import parsers.CP1251toUTF8;
import parsers.ExcerptFromText;

public class Catalogs {

    private final String DISC_LETTER;
    private final String PATH;

    /**
     * @param disc - буква диска
     * @param path - путь для фала с именами
     */
    public Catalogs(String disc, String path) {
        DISC_LETTER = disc;
        PATH = path;
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
                if (isGameFolder(f)) {
                    goodNameApplier(f);
                    writer.writeLine(f.getName() + "," + calculateSize(f));
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
     * @return дата выпуска игры
     */
    private void goodNameApplier(File f) {
        System.out.println(f.getName());
        HtmlExecutor exec = new HtmlExecutor();
        Map<String, String> googleAnswer = exec.findInGoogle("Википедия " + f.getName());
        
        Collection<String> googleLinks = googleAnswer.values();

        String series = new CP1251toUTF8().convert("серия");
        for (String link : googleLinks) {
            if (link.contains("wikipedia.org/wiki") && !link.contains(series)) {
                String newName = wikiExecutor(link);
                newName = newName.replace("(игра, ", "(");
                newName = newName.replace("[^\\w]", " ");
                
                if (!f.getName().equals(newName)) {
                    String newNameAndPath = f.getAbsolutePath().replace(f.getName(), newName);
                    System.out.println(newNameAndPath);
                    f.renameTo(new File(newNameAndPath));
                }
                return;
            }
        }
    }

    private String wikiExecutor(String link) {
        HtmlExecutor exec = new HtmlExecutor();
        String wikiContent = exec.contentExecutor(link);

        String title = "";
        ExcerptFromText excerpt = new ExcerptFromText();
        if (link.contains("ru.wikipedia.org")) {
            title = excerpt.TitleFromHtmlPage(wikiContent).replaceAll(" — Википедия", "");
        } else {
            title = excerpt.TitleFromHtmlPage(wikiContent).replaceAll(" - Wikipedia", "");
        }

        return title;
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

    /**
     * @param f - файл или папка, для которго необходимо посчитать размер
     * @return
     */
    public long calculateSize(File f) {
        long size = 0;
        if (f.listFiles() != null) {
            for (File f1 : f.listFiles()) {
                if (f1.isFile()) size += f1.length();
                if (f1.isDirectory()) size += calculateSize(f1);
            }
        }
        return size;
    }

    private boolean isGameFolder(File f) {
        if (!f.isDirectory()) return false;
        if ("$RECYCLE.BIN".equals(f.getName()) || "System Volume Information".equals(f.getName())) return false;
        return true;
    }

    public static void main(String[] args) {
        Catalogs catalogs = new Catalogs("g:", "D:/Games.exe"
                + "/One.csv");
        if (catalogs.createList()) System.out.println("Done");
    }
}
