package fileListings;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import fileOperation.WriterToFile;
import htmlConnector.HtmlExecutor;
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
                    String date = goodNameApplier(f);
                    writer.writeLine(f.getName() + "," + calculateSize(f) + "," + date);
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
    private String goodNameApplier(File f) {
        System.out.println(f.getName());
        HtmlExecutor exec = new HtmlExecutor();
        Map<String, String> googleAnswer = exec.findInGoogle("Википедия " + f.getName());
        WriterToFile writer = new WriterToFile("C:/" + f.getName());
        writer.write(googleAnswer);
        writer.close();
        
        Collection<String> googleLinks = googleAnswer.values();

        for (String link : googleLinks) {
            if (link.contains("wikipedia.org/wiki")) {
                String[] newName = wikiExecutor(link).split("~");
                String newNameAndPath = f.getAbsolutePath().replace(f.getName(), newName[0]);
                // f.renameTo(new File(newNameAndPath));
                return newName[1];
            }
        }
        return "";
    }

    private String wikiExecutor(String link) {
        HtmlExecutor exec = new HtmlExecutor();
        String wikiContent = exec.contentExecutor(link);

        ExcerptFromText excerpt = new ExcerptFromText();
        String n = excerpt.TitleFromHtmlPage(wikiContent).replaceAll(" — Википедия", "");

        List<String> dateArea = excerpt.extractExcerptsFromText(wikiContent, "Дата выпуска", "</span>");
        if (dateArea.isEmpty()) {
            System.out.println(link);
            System.exit(0);
        }
        List<String> dates = excerpt.extractExcerptsFromText(dateArea.get(0), "\">\\d", "\">", "</a>", "</a>");
        String date = dates.get(0) + " " + dates.get(1);

        return n + "~" + date;
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
        Catalogs catalogs = new Catalogs("g:", "d://Games.exe//One.csv");
        if (catalogs.createList()) System.out.println("Done");
    }
}
